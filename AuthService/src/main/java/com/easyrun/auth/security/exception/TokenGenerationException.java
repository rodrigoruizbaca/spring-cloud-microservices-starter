package com.easyrun.auth.security.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenGenerationException extends AuthenticationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TokenGenerationException(String msg, Throwable t) {
		super(msg, t);
		
	}

	public TokenGenerationException(String msg) {
		super(msg);
		
	}

}
