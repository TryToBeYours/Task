package org.example.mapper;

import org.example.dto.ticket.TicketResponse;
import org.example.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(source = "customer.username", target = "customerUsername")
    @Mapping(source = "assignedAgent.username", target = "agentUsername")
    @Mapping(source = "status", target = "status")
    TicketResponse toDto(Ticket ticket);
}
