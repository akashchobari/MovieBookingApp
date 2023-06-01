package com.akash.moviebookingapp.exception;

public class MovieBookingFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MovieBookingFailedException(Exception ex) {
		super(ex);
	}
	
}
