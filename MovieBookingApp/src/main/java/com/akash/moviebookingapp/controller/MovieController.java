package com.akash.moviebookingapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.akash.moviebookingapp.dto.BookMovieRequest;
import com.akash.moviebookingapp.dto.User;
import com.akash.moviebookingapp.entity.Movie;
import com.akash.moviebookingapp.exception.BookingExceedsAvailableSeats;
import com.akash.moviebookingapp.exception.MovieBookingFailedException;
import com.akash.moviebookingapp.service.MovieBookingService;

import io.jsonwebtoken.Claims;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/moviebooking")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MovieBookingService movieBookingService;
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllMovies(){
		List<Movie> movies =movieBookingService.getAllMovies();
		if(movies.size()==0)
			return new ResponseEntity<String>("Movies not found", HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(movies);
	}
	@GetMapping("/movies/search/{movieId}")
	public ResponseEntity<?> getMovieById(@PathVariable("movieId") int movieId){
		Optional<Movie> movie = movieBookingService.getMovieById(movieId);
		if(movie.isEmpty())
			return new ResponseEntity<String>("Movie not found", HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(movie);

	}
	
	
	@PutMapping("/{movieName}/update/{ticketId}")
	public ResponseEntity<?> updateTicket(@PathVariable("movieName") String movieName, @PathVariable("ticketId") int ticketId){
		return ResponseEntity.ok(movieName+" "+ticketId);
	}
	
	public User getUser(String username) {
		return restTemplate.getForObject("http://localhost:8082/api/auth/moviebooking/getUser/"+username, User.class);
	}
	
	@PostMapping("/{admin}/addmovie")
	public ResponseEntity<?> addMovie(@PathVariable("admin") String adminName, @RequestBody Movie movie, HttpServletRequest req ){
		Claims claims = (Claims)req.getAttribute("username"); 
		final String subject = claims.getSubject();
		//get user by subject(name) and check the role if admin then only allow else throw exception not authorized
		User user = getUser(subject);
		if(user.getUserRole().equalsIgnoreCase("ADMIN")) {
			try {
				movie.setStatus("AVAILABLE");
				movieBookingService.addMovie(adminName, movie);
			} catch(Exception ex) {
				return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return ResponseEntity.ok("Movie added successfully");
		}
		return new ResponseEntity<String>("You are not authorized to access this page", HttpStatus.UNAUTHORIZED);			
	}
	
	@DeleteMapping("/{admin}/delete/{movieId}")
	public ResponseEntity<?> deleteMovieById(@PathVariable("admin") String admin, @PathVariable("movieId") int movieId, HttpServletRequest req){
//		System.out.println(movieId);
		Claims claims = (Claims)req.getAttribute("username"); 
		final String subject = claims.getSubject();
		//get user by subject(name) and check the role if admin then only allow else throw exception not authorized
		User user = getUser(subject);
		if(user.getUserRole().equalsIgnoreCase("ADMIN")) {
			try {
				movieBookingService.deleteMovie(admin, movieId);
			} catch(Exception ex) {
				return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return ResponseEntity.ok("Movie deleted successfully");		
		}
		return new ResponseEntity<String>("You are not authorized to access this page", HttpStatus.UNAUTHORIZED);
	}

	
} 
