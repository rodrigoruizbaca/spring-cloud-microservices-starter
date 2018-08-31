package com.easyrun.auth.security.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidUsernamePasswordException extends AuthenticationException {

	public InvalidUsernamePasswordException(String msg, Throwable t) {
		super(msg, t);
		
	}

	public InvalidUsernamePasswordException(String msg) {
		super(msg);
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
