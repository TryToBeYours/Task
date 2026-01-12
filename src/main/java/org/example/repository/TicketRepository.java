package org.example.repository;

import org.example.model.Ticket;
import org.springframework.data.jpa.repository.*;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("""
    SELECT t FROM Ticket t
    JOIN FETCH t.assignedAgent a
    JOIN FETCH a.roles
""")
    List<Ticket> findAllWithAssigneeAndRoles();

}
