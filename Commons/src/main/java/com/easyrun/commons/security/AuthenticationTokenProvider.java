package com.easyrun.commons.security;

import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.JwksVerificationKeyResolver;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.easyrun.commons.dto.ConfigurationDto;
import com.easyrun.commons.dto.UserDto;
import com.easyrun.commons.security.exception.MalformedTokenException;
import com.easyrun.commons.service.AuthenticationService;
@Component
public class AuthenticationTokenProvider implements AuthenticationProvider {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AuthenticationToken auth = (AuthenticationToken) authentication;
		try {
			// Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
			List<ConfigurationDto> configs = authenticationService.getPublicKeys();
			List<JsonWebKey> jsonWebKeys = new ArrayList<>();
			for (ConfigurationDto publicKey : configs) {
				PublicJsonWebKey jsonWebKey = PublicJsonWebKey.Factory.newPublicJwk(publicKey.getPublicKey());
	            RsaJsonWebKey rsaJsonWebKey = new RsaJsonWebKey((RSAPublicKey) jsonWebKey.getPublicKey());
	            rsaJsonWebKey.setKeyId(publicKey.getHeaderKeyId());
	            jsonWebKeys.add(rsaJsonWebKey);
			}
			
            JwksVerificationKeyResolver jwksVerificationKeyResolver = new JwksVerificationKeyResolver(jsonWebKeys);
		    // Give the JWK a Key ID (kid), which is just the polite thing to do
		    
			JwtConsumer jwtConsumer = new JwtConsumerBuilder()
		            .setRequireExpirationTime() // the JWT must have an expiration time
		            .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
		            .setExpectedIssuer("easyrun.auth") // whom the JWT needs to have been issued by
		            .setExpectedAudience("easyrun.user") // to whom the JWT is intended for
		            .setVerificationKeyResolver(jwksVerificationKeyResolver)// verify the signature with the public key
		            .build();
			
			JwtClaims jwtClaims = jwtConsumer.processToClaims(((UserDto)auth.getPrincipal()).getToken());
			
			List<String> scope = jwtClaims.getClaimValue("scope", List.class);
			List<GrantedAuthority> authorities = scope.stream()
                   .map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList());
			String username = jwtClaims.getSubject();
			UserDto user = new UserDto();
			user.setUsername(username);
			user.setId(jwtClaims.getClaimValue("id", String.class));
			AuthenticationToken result = new AuthenticationToken(user, authorities);
			result.setAuthenticated(true);
			return result;
		} catch (JoseException | InvalidJwtException | MalformedClaimException e) {
			throw new MalformedTokenException("Invalid token", e);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (AuthenticationToken.class
				.isAssignableFrom(authentication));
	}

}
