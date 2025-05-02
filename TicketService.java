package com.example.demo.service;

import com.example.demo.entity.Ticket;
import com.example.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public String deleteTicket(Long id) {
        ticketRepository.deleteById(id);
        return "Ticket deleted successfully.";
    }
    
    public Ticket bookTicket(Ticket ticket) {
        boolean seatTaken = ticketRepository.existsBySeatNumberAndEvent(ticket.getSeatNumber(), ticket.getEvent());
        if (seatTaken) throw new RuntimeException("Seat already booked");
        return ticketRepository.save(ticket);
    }

}
