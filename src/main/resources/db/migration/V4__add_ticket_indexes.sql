CREATE INDEX idx_ticket_status
ON tickets(status);
CREATE INDEX idx_ticket_assigned_agent
ON tickets(assigned_agent_id);
