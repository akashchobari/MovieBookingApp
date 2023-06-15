package com.akash.moviebookingapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.moviebookingapp.dto.BookMovieRequest;
import com.akash.moviebookingapp.entity.Movie;
import com.akash.moviebookingapp.entity.Ticket;
import com.akash.moviebookingapp.service.MovieBookingService;
import com.akash.moviebookingapp.service.PublisherService;

@RestController
@RequestMapping("/api/v1/moviebooking")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {
	
	@Autowired
	private MovieBookingService movieBookingService;

	@Autowired
	private PublisherService ps;
	
	@PostMapping("/{movieName}/book")
	public ResponseEntity<?> bookTicket(@PathVariable("movieName") String movieName , @RequestBody BookMovieRequest request){
//		System.out.println(movieName);
//		System.out.println(request.getMovieId());
//		System.out.println(request.getNumberOfTickets());
//		System.out.println(movieName);
		try {
			Optional<Movie> movie = movieBookingService.getMovieById(request.getMovieId());
			if(movie.isEmpty())
				return new ResponseEntity<String>("Please enter valid Movie ID", HttpStatus.BAD_REQUEST);
			movieBookingService.bookMovieTickets(movie.get(),request);		
		}
		catch(Exception ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ps.setTemp(request.getNumberOfTickets() + " Movie tickets booked successfully");
		return ResponseEntity.ok("Movie tickets booked successfully");
	}

	@GetMapping("/getAllTickets")
	public ResponseEntity<?> fetchAllTickets(){
		List<Ticket> tickets = movieBookingService.getAllTickets();
		return ResponseEntity.ok(tickets);
	}

	
}
