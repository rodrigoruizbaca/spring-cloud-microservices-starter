package com.easyrun.commons.security.exception;

import org.springframework.security.core.AuthenticationException;

public class MalformedTokenException extends AuthenticationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MalformedTokenException(String msg, Throwable t) {
		super(msg, t);
		
	}

	public MalformedTokenException(String msg) {
		super(msg);
		
	}


}
