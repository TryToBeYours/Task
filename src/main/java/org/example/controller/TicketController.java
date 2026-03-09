package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.ticket.AssignTicketRequest;
import org.example.dto.ticket.CreateTicketRequest;
import org.example.dto.ticket.TicketResponse;
import org.example.model.TicketStatus;
import org.example.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<TicketResponse> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @PostMapping
    public TicketResponse createTicket(@Valid @RequestBody CreateTicketRequest request) {
        return ticketService.createTicket(request);
    }

    @PatchMapping("/{ticketId}/assign")
    public TicketResponse assignTicket(
            @PathVariable Long ticketId,
            @Valid @RequestBody AssignTicketRequest request) {
        return ticketService.assignTicket(ticketId, request);
    }

    @PatchMapping("/{ticketId}/status")
    public TicketResponse updateTicketStatus(
            @PathVariable Long ticketId,
            @RequestParam TicketStatus status) {
        return ticketService.updateTicketStatus(ticketId, status);
    }
}