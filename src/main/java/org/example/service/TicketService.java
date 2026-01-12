package org.example.service;

import org.example.dto.ticket.TicketResponse;
import org.example.event.TicketCreatedEvent;
import org.example.event.TicketResolvedEvent;
import org.example.mapper.TicketMapper;
import org.example.model.Ticket;
import org.example.model.TicketStatus;
import org.example.model.User;
import org.example.repository.TicketRepository;
import org.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private static final Logger logger =
            LoggerFactory.getLogger(TicketService.class);

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;
    private final ApplicationEventPublisher eventPublisher;

    public TicketService(
            TicketRepository ticketRepository,
            UserRepository userRepository,
            TicketMapper ticketMapper,
            ApplicationEventPublisher eventPublisher
    ) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.ticketMapper = ticketMapper;
        this.eventPublisher = eventPublisher;
    }

    public List<TicketResponse> getAllTickets() {
        logger.info("Fetching all tickets");

        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    public TicketResponse createTicket(Ticket ticket) {

        ticket.setStatus(TicketStatus.OPEN);

        Ticket savedTicket = ticketRepository.save(ticket);

        logger.info(
                "Ticket created | id={} | subject={}",
                savedTicket.getId(),
                savedTicket.getSubject()
        );

        eventPublisher.publishEvent(new TicketCreatedEvent(savedTicket));

        return ticketMapper.toDto(savedTicket);
    }

    public TicketResponse assignTicket(Long ticketId, Long agentId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User agent = userRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        ticket.setAssignedAgent(agent);
        ticket.setStatus(TicketStatus.IN_PROGRESS);

        Ticket updatedTicket = ticketRepository.save(ticket);

        logger.info(
                "Ticket assigned | ticketId={} | agentId={}",
                ticketId,
                agentId
        );

        return ticketMapper.toDto(updatedTicket);
    }

    public TicketResponse changeStatus(Long ticketId, TicketStatus status) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(status);

        Ticket updatedTicket = ticketRepository.save(ticket);

        logger.info(
                "Ticket status changed | ticketId={} | newStatus={}",
                ticketId,
                status
        );

        return ticketMapper.toDto(updatedTicket);
    }

    @PreAuthorize(
            "hasRole('ADMIN') or " +
                    "@ticketSecurity.isAssignedAgent(#ticketId, authentication.name)"
    )
    public TicketResponse resolveTicket(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(TicketStatus.RESOLVED);

        Ticket saved = ticketRepository.save(ticket);

        logger.info(
                "Ticket resolved | ticketId={}",
                ticketId
        );

        eventPublisher.publishEvent(new TicketResolvedEvent(saved));

        return ticketMapper.toDto(saved);
    }
}
