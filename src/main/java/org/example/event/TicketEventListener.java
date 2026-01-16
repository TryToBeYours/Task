package org.example.event;

import org.example.model.Ticket;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TicketEventListener {

    @EventListener
    public void handleTicketCreated(TicketCreatedEvent event) {
        Ticket ticket = event.getTicket();

        String customerUsername = ticket.getCustomer() != null
                ? ticket.getCustomer().getUsername()
                : "UNKNOWN";

        System.out.println(
                "Ticket CREATED | Title: " + ticket.getTitle() +
                        " | Customer: " + customerUsername +
                        " | Status: " + ticket.getStatus()
        );
    }

    @EventListener
    public void handleTicketResolved(TicketResolvedEvent event) {
        Ticket ticket = event.getTicket();

        String agentUsername = ticket.getAssignedAgent() != null
                ? ticket.getAssignedAgent().getUsername()
                : "UNASSIGNED";

        System.out.println(
                "Ticket RESOLVED | Title: " + ticket.getTitle() +
                        " | Agent: " + agentUsername +
                        " | Status: " + ticket.getStatus()
        );
    }
}
