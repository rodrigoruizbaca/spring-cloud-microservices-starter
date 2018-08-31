package com.easyrun.auth.security;

import java.util.Arrays;
import java.util.List;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.easyrun.auth.model.User;
import com.easyrun.auth.repository.UserRepository;
import com.easyrun.auth.security.exception.InvalidUsernamePasswordException;
import com.easyrun.auth.security.exception.TokenGenerationException;
import com.easyrun.auth.transformer.UserTransformer;
import com.easyrun.commons.dto.UserDto;
import com.easyrun.commons.repository.ConfigurationRepository;
import com.google.common.collect.Lists;


@Component
public class UsernamePasswordAuthentitationProvider implements AuthenticationProvider {

	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected UserTransformer userTransformer;
	
	@Autowired
	protected ConfigurationRepository configurationRepository;
	
	@Autowired
	protected RsaJsonWebKey rsaJsonWebKey;
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		UsernamePasswordAuthentication authentication = (UsernamePasswordAuthentication) auth;
		UserDto user = (UserDto) authentication.getPrincipal();
		if (user != null && user.getUsername() != null && user.getPassword() != null) {
			User u = userRepository.getByUsername(user.getUsername());
			if (u != null && passwordEncoder.matches(user.getPassword(), u.getPassword())) {
				try {
					UserDto resultUser = userTransformer.toDto(u);
					JwtClaims claims = new JwtClaims();
				    claims.setIssuer("easyrun.auth");  // who creates the token and signs it
				    claims.setAudience("easyrun.user"); // to whom the token is intended to be sent
				    claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
				    claims.setGeneratedJwtId(); // a unique identifier for the token
				    claims.setIssuedAtToNow();  // when the token was issued/created (now)
				    claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
				    claims.setSubject(resultUser.getUsername()); // additional claims/attributes about the subject can be added
				    claims.setClaim("id", resultUser.getId());
				    //Get roles
				    List<String> scope = Arrays.asList("easyrun.user.token");
				    claims.setStringListClaim("scope", scope);
				    JsonWebSignature jws = new JsonWebSignature();
				    // The payload of the JWS is JSON content of the JWT Claims
				    jws.setPayload(claims.toJson());
	
				    // The JWT is signed using the private key
				    jws.setKey(rsaJsonWebKey.getPrivateKey());
				    // Set the Key ID (kid) header because it's just the polite thing to do.
				    // We only have one key in this example but a using a Key ID helps
				    // facilitate a smooth key rollover process
				    jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
	
				    // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
				    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
				    
				    // Sign the JWS and produce the compact serialization or the complete JWT/JWS
				    // representation, which is a string consisting of three dot ('.') separated
				    // base64url-encoded parts in the form Header.Payload.Signature
				    // If you wanted to encrypt it, you can simply set this jwt as the payload
				    // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
					String jwt = jws.getCompactSerialization();
					resultUser.setToken(jwt);
					List<GrantedAuthority> authorities = Lists.newArrayList();
					authorities.add(new SimpleGrantedAuthority("easyrun.user.token"));
					UsernamePasswordAuthentication resultAuth = new UsernamePasswordAuthentication(resultUser, authorities);
					resultAuth.setAuthenticated(true);
					return resultAuth;
				} catch (JoseException e) {
					throw new TokenGenerationException("Cant generate the token");
				}
				
			} else {
				throw new InvalidUsernamePasswordException("Username or Password invalid");
			}
		} else {
			throw new InvalidUsernamePasswordException("Username or Password invalid");
		}
	}

	@Override
	public boolean supports(Class<?> auth) {
		return (UsernamePasswordAuthentication.class
				.isAssignableFrom(auth));
	}

}
