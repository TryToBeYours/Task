package org.example.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TicketDTO {
    private Long id;
    private String title;
    private String description;
    private Integer priority;
    private String customerUsername;
    private String assignedAgentUsername;
    private String status;
    private String createdAt;

}

