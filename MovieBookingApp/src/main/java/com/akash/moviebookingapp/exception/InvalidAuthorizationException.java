package com.akash.moviebookingapp.exception;

public class InvalidAuthorizationException extends RuntimeException{
	public InvalidAuthorizationException(String msg) {
		super(msg);
	}
}
