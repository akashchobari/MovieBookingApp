package com.akash.usermanagement.exception;

public class InvalidLoginAttemptException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidLoginAttemptException() {
		super("please enter valid credentials");
	}
}
