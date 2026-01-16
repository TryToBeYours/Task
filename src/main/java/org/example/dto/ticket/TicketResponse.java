package org.example.dto.ticket;

public class TicketResponse {

    private Long id;
    private String title;
    private String description;
    private Integer priority;
    private String customerUsername;
    private String assignedAgentUsername;
    private String status;
    private String createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }

    public String getCustomerUsername() { return customerUsername; }
    public void setCustomerUsername(String customerUsername) { this.customerUsername = customerUsername; }

    public String getAssignedAgentUsername() { return assignedAgentUsername; }
    public void setAssignedAgentUsername(String assignedAgentUsername) { this.assignedAgentUsername = assignedAgentUsername; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}