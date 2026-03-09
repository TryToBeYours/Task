package service;

import org.example.dto.ticket.CreateTicketRequest;
import org.example.dto.ticket.TicketResponse;
import org.example.mapper.TicketMapper;
import org.example.model.Ticket;
import org.example.model.TicketPriority;
import org.example.model.TicketStatus;
import org.example.model.User;
import org.example.repository.TicketRepository;
import org.example.repository.UserRepository;
import org.example.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TicketMapper ticketMapper;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void shouldCreateTicket() {
        // Arrange
        CreateTicketRequest request = new CreateTicketRequest();
        request.setSubject("Login Issue");
        request.setDescription("Cannot login");
        request.setCustomerId(1L);
        request.setPriority(TicketPriority.HIGH);

        User customer = new User();
        customer.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(customer));

        Ticket savedTicket = Ticket.builder()
                .title(request.getSubject())
                .description(request.getDescription())
                .priority(request.getPriority())
                .status(TicketStatus.OPEN)
                .customer(customer)
                .build();

        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        TicketResponse response = new TicketResponse();
        response.setTitle(savedTicket.getTitle());
        response.setStatus(savedTicket.getStatus().name()); // DTO uses String
        when(ticketMapper.toTicketResponse(savedTicket)).thenReturn(response);

        // Act
        TicketResponse result = ticketService.createTicket(request);

        // Assert
        assertNotNull(result);
        assertEquals("Login Issue", result.getTitle());
        assertEquals(TicketStatus.OPEN.name(), result.getStatus());

        verify(userRepository).findById(1L);
        verify(ticketRepository).save(any(Ticket.class));
        verify(ticketMapper).toTicketResponse(savedTicket);
    }
}