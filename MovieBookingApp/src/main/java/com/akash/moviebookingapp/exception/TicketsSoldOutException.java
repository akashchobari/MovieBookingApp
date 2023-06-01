package com.akash.moviebookingapp.exception;

public class TicketsSoldOutException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TicketsSoldOutException(String msg) {
		super(msg);
	}
}
