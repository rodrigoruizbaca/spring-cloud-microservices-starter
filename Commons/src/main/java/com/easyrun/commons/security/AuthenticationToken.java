package com.easyrun.commons.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.easyrun.commons.dto.UserDto;

public class AuthenticationToken extends AbstractAuthenticationToken {
	
	private UserDto user;
	

	public AuthenticationToken(UserDto user, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.user = user;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return user;
	}

}
