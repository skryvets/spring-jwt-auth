package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AuthController {

    private final AuthService authService;

    AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public TokenResponseDto authenticateWithToken(@RequestBody TokenRequestDto request) {
        return authService.authenticateWithToken(request);
    }

}
