package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.auth.AuthResponse;
import org.example.dto.auth.RegisterRequest;
import org.example.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthResponse> registerAdmin(
            @RequestBody @Valid RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request, "ADMIN")
        );
    }
    @PostMapping("/register/agent")
    public ResponseEntity<AuthResponse> registerAgent(
            @RequestBody @Valid RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request, "AGENT")
        );
    }
    @PostMapping("/register/customer")
    public ResponseEntity<AuthResponse> registerCustomer(
            @RequestBody @Valid RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request, "CUSTOMER")
        );
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody Map<String, String> loginRequest) {

        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        String token = authService.login(username, password);

        return ResponseEntity.ok(Map.of(
                "username", username,
                "token", token
        ));
    }
}
