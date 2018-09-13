package com.easyrun.commons.exception;

public class BadFilterException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadFilterException(String msg, Throwable th) {
		super(msg, th);
	}

	public BadFilterException(String msg) {
		super(msg);
	}
}
