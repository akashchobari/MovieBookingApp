package com.akash.moviebookingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.akash.moviebookingapp.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>{
	
}
