package com.example.demo;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AppController {

    @GetMapping("/private")
    public Map<String, String> privateEndpoint() {
        return Map.of("message", "Hello From Private Endpoint");
    }

    @GetMapping("/public")
    public Map<String, String> publicEndpoint() {
        return Map.of("message", "Hello From Public Endpoint");
    }
}
