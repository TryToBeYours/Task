package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.ticket.CreateTicketRequest;
import org.example.dto.ticket.TicketResponse;
import org.example.model.TicketPriority;
import org.example.model.TicketStatus;
import org.example.service.TicketService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TicketControllerTest {

    private MockMvc mockMvc;
    private TicketService ticketService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        ticketService = Mockito.mock(TicketService.class);
        TicketController ticketController = new TicketController(ticketService);

        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    @Test
    void shouldCreateTicket() throws Exception {

        TicketResponse response = new TicketResponse();
        response.setTitle("Login Issue");
        response.setStatus(TicketStatus.OPEN.name());

        Mockito.when(ticketService.createTicket(any(CreateTicketRequest.class)))
                .thenReturn(response);

        CreateTicketRequest request = new CreateTicketRequest();
        request.setSubject("Login Issue");
        request.setDescription("Cannot login");
        request.setCustomerId(1L);
        request.setPriority(TicketPriority.HIGH);

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Login Issue"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }
}