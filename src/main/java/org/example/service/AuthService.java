package org.example.service;

import org.example.dto.auth.LoginRequest;
import org.example.dto.auth.LoginResponse;
import org.example.exception.ResourceNotFoundException;
import org.example.exception.UnauthorizedActionException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "username", loginRequest.getUsername()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UnauthorizedActionException("Invalid credentials");
        }

        String token = jwtUtils.generateJwt(user.getUsername());
        return new LoginResponse(token, user.getUsername());
    }
}
