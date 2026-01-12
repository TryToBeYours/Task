package org.example.event;

import org.example.model.Ticket;
import org.example.service.EmailServices;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TicketEventListener {

    private final EmailServices emailService;

    public TicketEventListener(EmailServices emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleTicketAssigned(TicketCreatedEvent event) {

        Ticket ticket = event.ticket();

        if (ticket.getAssignedAgent() != null) {
            emailService.sendEmail(
                    ticket.getAssignedAgent().getUsername(),
                    "New Ticket Assigned",
                    "Ticket: " + ticket.getSubject()
            );
        }
    }

    @EventListener
    public void handleTicketResolved(TicketResolvedEvent event) {

        Ticket ticket = event.ticket();

        if (ticket.getCustomer() != null) {
            emailService.sendEmail(
                    ticket.getCustomer().getUsername(),
                    "Ticket Resolved",
                    "Your ticket '" + ticket.getSubject() + "' has been resolved."
            );
        }
    }
}
