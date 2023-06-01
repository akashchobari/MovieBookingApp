package com.akash.moviebookingapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.moviebookingapp.dto.BookMovieRequest;
import com.akash.moviebookingapp.entity.Movie;
import com.akash.moviebookingapp.entity.Ticket;
import com.akash.moviebookingapp.exception.BookingExceedsAvailableSeats;
import com.akash.moviebookingapp.exception.TicketsSoldOutException;
import com.akash.moviebookingapp.repository.MovieRepository;
import com.akash.moviebookingapp.repository.TicketRepository;

@Service
public class MovieBookingServiceImpl implements MovieBookingService {
	
	@Autowired
	private MovieRepository movieRepo;
	
	@Autowired
	private TicketRepository ticketRepo;

	@Override
	public List<Movie> getAllMovies() {
		return movieRepo.findAll();
	}

	@Override
	public Optional<Movie> getMovieById(int id) {
		return movieRepo.findById(id);
	}

	@Override
	public void bookMovieTickets(Movie movie, BookMovieRequest request) {
		
		if(movie.getAvailableSeats()<request.getNumberOfTickets()) {
			if(movie.getAvailableSeats()==0)
				throw new TicketsSoldOutException("tickets sold out");
			else 
				throw new BookingExceedsAvailableSeats("you cannot book more than "+movie.getAvailableSeats()+" seats");
		}
		
		
		//updating seats after booking
		int updatedAvailableSeats = movie.getAvailableSeats()-request.getNumberOfTickets();
		int updatedBookedSeats = movie.getBookedSeats()+request.getNumberOfTickets();
		
		//else book and store tickets
		Ticket ticket = new Ticket();
		
		ticket.setTotalSeats(100);
		ticket.setMovieName(movie.getMovieName());
		ticket.setBookedSeats(request.getNumberOfTickets());
		ticket.setAvailableSeats(updatedAvailableSeats);
		
		//store movie field in ticket 
		movie.setAvailableSeats(updatedAvailableSeats);
		movie.setBookedSeats(updatedBookedSeats);
		if(movie.getAvailableSeats()==0)
			movie.setStatus("SOLD");
		
		ticket.setMovie(movie);
		
		//updates both ticket and movie table by relationship mapping
		ticketRepo.save(ticket);
		
	}

	@Override
	public String updateTicket(int ticketId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMovie(String adminName, Movie movie) {
		movieRepo.save(movie);			
	}

	@Override
	public void deleteMovie(String adminName, int movieId) {
		movieRepo.deleteById(movieId);
	}

}
