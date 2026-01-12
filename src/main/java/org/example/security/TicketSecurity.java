package org.example.security;

import org.example.model.Ticket;
import org.example.repository.TicketRepository;
import org.springframework.stereotype.Component;

@Component
public class TicketSecurity {

    private final TicketRepository ticketRepository;

    public TicketSecurity(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public boolean isAssignedAgent(Long ticketId, String username) {
        return ticketRepository.findById(ticketId)
                .map(ticket ->
                        ticket.getAssignedAgent() != null &&
                                ticket.getAssignedAgent().getUsername().equals(username)
                )
                .orElse(false);
    }
}
