package com.easyrun.commons.security.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

	public InvalidTokenException(String msg, Throwable th) {
		super(msg, th);
	}

	public InvalidTokenException(String msg) {
		super(msg);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
