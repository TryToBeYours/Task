package org.example.repository;

import org.example.model.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"roles"})
    List<User> findAll();

    @Query("""
        SELECT u FROM User u 
        LEFT JOIN FETCH u.roles 
        LEFT JOIN FETCH u.assignedTickets 
        WHERE u.username = :username
    """)
    Optional<User> findByUsernameWithDetails(@Param("username") String username);
}
