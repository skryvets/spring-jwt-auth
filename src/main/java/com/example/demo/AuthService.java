package com.example.demo;

import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
class AuthService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthService.class);

    private final TokenService tokenService;
    private final UserDetailsManager userDetailsManager;

    AuthService(final TokenService tokenService, final UserDetailsManager userDetailsManager) {
        this.tokenService = tokenService;
        this.userDetailsManager = userDetailsManager;
    }

    public TokenResponseDto authenticateWithToken(TokenRequestDto tokenRequest) {
        final UserDetails userDetails;
        try {
            userDetails = userDetailsManager.loadUserByUsername(tokenRequest.email());
        } catch (UsernameNotFoundException e) {
            // Log specific error for debugging/monitoring
            log.error("User not found: {}", tokenRequest.email());
            // Return generic message to client for security
            throw new RuntimeException("Invalid credentials");
        }

        // SECURITY RISK: Using plain text password comparison because we're using
        // InMemoryUserDetailsManager with non-encoded passwords.
        // In production use: passwordEncoder.matches(tokenRequest.password(), userDetails.getPassword())
        if (!Objects.equals(userDetails.getPassword(), tokenRequest.password())) {
            // Log the specific error for debugging/monitoring
            log.error("Password mismatch for user: {}", tokenRequest.email());
            // Return generic message to client for security
            throw new RuntimeException("Invalid credentials");
        }
        var permissions = extractPermissions(userDetails);
        var jwtToken = tokenService.generateToken(userDetails.getUsername(), permissions);
        return new TokenResponseDto(jwtToken);
    }

    private String extractPermissions(UserDetails userDetails) {
        return userDetails
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
    }
}