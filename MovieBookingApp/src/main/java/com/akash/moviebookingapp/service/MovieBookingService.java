package com.akash.moviebookingapp.service;

import java.util.List;
import java.util.Optional;

import com.akash.moviebookingapp.dto.BookMovieRequest;
import com.akash.moviebookingapp.entity.Movie;

public interface MovieBookingService {
	public List<Movie> getAllMovies();
	public Optional<Movie> getMovieById(int id);
	public void bookMovieTickets(Movie movie,BookMovieRequest request);
	public String updateTicket(int ticketId);
	public void addMovie(String adminName, Movie movie);
	public void deleteMovie(String adminName, int movieId);
}
