package com.easyrun.commons.exception;

public class DuplicateEntityException extends Exception {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateEntityException(String msg, Throwable th) {
		super(msg, th);
	}

	public DuplicateEntityException(String msg) {
		super(msg);
	}
}
