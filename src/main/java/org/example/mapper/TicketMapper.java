package org.example.mapper;

import org.example.dto.ticket.TicketResponse;
import org.example.model.Ticket;
import org.example.model.TicketPriority;
import org.example.model.TicketStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(source = "customer.username", target = "customerUsername")
    @Mapping(source = "assignedAgent.username", target = "assignedAgentUsername")
    @Mapping(source = "priority", target = "priority", qualifiedByName = "priorityToInt")
    @Mapping(source = "status", target = "status", qualifiedByName = "statusToString")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    TicketResponse toTicketResponse(Ticket ticket);

    List<TicketResponse> toDtoList(List<Ticket> tickets);

    @Named("priorityToInt")
    default Integer priorityToInt(TicketPriority priority) {
        if (priority == null) return null;
        return switch (priority) {
            case LOW -> 1;
            case MEDIUM -> 2;
            case HIGH -> 3;
        };
    }

    @Named("statusToString")
    default String statusToString(TicketStatus status) {
        return status == null ? null : status.name();
    }
}