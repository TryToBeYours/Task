package org.example.model;

import jakarta.persistence.*;
@Entity
@Table(
        name = "tickets",
        indexes = {
                @Index(name = "idx_ticket_status", columnList = "status"),
                @Index(name = "idx_ticket_assigned_agent", columnList = "assigned_agent_id")
        }
)

public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ticketpriority priority = Ticketpriority.MEDIUM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_agent_id")
    private User assignedAgent;

    public Long getId() { return id; }
    public String getSubject() { return subject; }
    public String getDescription() { return description; }
    public TicketStatus getStatus() { return status; }
    public Ticketpriority getPriority() { return priority; }
    public User getCustomer() { return customer; }
    public User getAssignedAgent() { return assignedAgent; }

    public void setSubject(String subject) { this.subject = subject; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(TicketStatus status) { this.status = status; }
    public void setPriority(Ticketpriority priority) { this.priority = priority; }
    public void setCustomer(User customer) { this.customer = customer; }
    public void setAssignedAgent(User assignedAgent) { this.assignedAgent = assignedAgent; }
}
