package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Event;
import com.example.demo.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>
{

	boolean existsBySeatNumberAndEvent(String seatNumber, Event event);

}
