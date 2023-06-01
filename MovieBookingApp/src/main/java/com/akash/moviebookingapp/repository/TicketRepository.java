package com.akash.moviebookingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.moviebookingapp.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
