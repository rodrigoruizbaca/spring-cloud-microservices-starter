package com.easyrun.auth.oauth2.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.easyrun.commons.dto.ClientDto;

import lombok.Getter;

public class ClientAuthentication extends AbstractAuthenticationToken{

	@Getter
	private String token;
	private ClientDto client;
	
	public ClientAuthentication(ClientDto client, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.client = client;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7300235353921207425L;

	
	
	@Override
	public Object getCredentials() {
		return super.getAuthorities();
	}

	@Override
	public Object getPrincipal() {
		return client;
	}

}
