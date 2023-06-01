package com.akash.moviebookingapp.exception;

public class BookingExceedsAvailableSeats extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BookingExceedsAvailableSeats(String message) {
		super(message);
	}
	
}
