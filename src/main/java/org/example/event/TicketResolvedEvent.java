package org.example.event;

import org.example.model.Ticket;

public record TicketResolvedEvent(Ticket ticket) {}
