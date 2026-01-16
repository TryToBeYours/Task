package org.example.security;

import org.example.model.Ticket;
import org.example.repository.TicketRepository;
import org.springframework.stereotype.Component;

@Component("ticketSecurity")
public class TicketSecurity {

    private final TicketRepository ticketRepository;

    public TicketSecurity(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public boolean isAssignedAgent(Long ticketId, String username) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket.getAssignedAgent() == null) {
            return false;
        }

        return ticket.getAssignedAgent()
                .getUsername()
                .equals(username);
    }
}
