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
            throw new RuntimeException(
                String.format("User with email: %s was not found", tokenRequest.email()));
        }
        if (!Objects.equals(userDetails.getPassword(), tokenRequest.password())) {
            throw new RuntimeException(String.format("Password mismatch for user: %s", tokenRequest.email()));
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