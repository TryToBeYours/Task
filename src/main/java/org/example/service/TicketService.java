package org.example.service;

import org.example.dto.ticket.AssignTicketRequest;
import org.example.dto.ticket.CreateTicketRequest;
import org.example.dto.ticket.TicketResponse;
import org.example.mapper.TicketMapper;
import org.example.model.Ticket;
import org.example.model.TicketStatus;
import org.example.model.User;
import org.example.repository.TicketRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.ticketMapper = ticketMapper;
    }

    public List<TicketResponse> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return ticketMapper.toDtoList(tickets);
    }

    public TicketResponse createTicket(CreateTicketRequest request) {
        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Ticket ticket = Ticket.builder()
                .title(request.getSubject())
                .description(request.getDescription())
                .status(TicketStatus.OPEN)
                .priority(request.getPriority())
                .customer(customer)
                .build();

        Ticket saved = ticketRepository.save(ticket);
        return ticketMapper.toTicketResponse(saved);
    }

    public TicketResponse assignTicket(Long ticketId, AssignTicketRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User agent = userRepository.findById(request.getAgentId())
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        ticket.setAssignedAgent(agent);
        Ticket saved = ticketRepository.save(ticket);
        return ticketMapper.toTicketResponse(saved);
    }

    public TicketResponse updateTicketStatus(Long ticketId, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(status);
        Ticket saved = ticketRepository.save(ticket);
        return ticketMapper.toTicketResponse(saved);
    }
}