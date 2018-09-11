package com.easyrun.commons.exception;

public class EntityNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String msg, Throwable th) {
		super(msg, th);
	}

	public EntityNotFoundException(String msg) {
		super(msg);
	}
}
