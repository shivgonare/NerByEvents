package com.example.demo.controller;

import com.example.demo.entity.Ticket;
import com.example.demo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired private TicketService ticketService;

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.saveTicket(ticket);
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id) {
        return ticketService.deleteTicket(id);
    }
    
    @PostMapping("/book")
    public Ticket bookTicket(@RequestBody Ticket ticket) {
        return ticketService.bookTicket(ticket);
    }

}
