package com.easyrun.auth.security;

import javax.servlet.annotation.WebFilter;

import org.springframework.security.authentication.AuthenticationManager;

import com.easyrun.commons.security.AuthenticationTokenFilter;
@WebFilter(urlPatterns = {"/auth/oper/*"})
public class AuthenticationTokenAuthFilter extends AuthenticationTokenFilter {

	public AuthenticationTokenAuthFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);		
	}

}
