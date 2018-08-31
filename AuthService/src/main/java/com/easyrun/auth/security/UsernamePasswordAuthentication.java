package com.easyrun.auth.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.easyrun.commons.dto.UserDto;

import lombok.Getter;

public class UsernamePasswordAuthentication extends AbstractAuthenticationToken {
	@Getter
	private String token;
	private UserDto user;
	
	public UsernamePasswordAuthentication(UserDto user, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.user = user;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7300235353921207425L;

	
	
	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}
}
