package org.example.controller;

import org.example.dto.auth.LoginRequest;
import org.example.dto.auth.LoginResponse;
import org.example.exception.ResourceNotFoundException;
import org.example.exception.UnauthorizedActionException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", loginRequest.getUsername()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UnauthorizedActionException("Invalid credentials");
        }

        String token = jwtUtils.generateJwt(user.getUsername());
        return new LoginResponse(token, user.getUsername());
    }
}
