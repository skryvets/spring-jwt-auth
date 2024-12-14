package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

record TokenResponseDto(
    @JsonProperty("jwt_token")
    String jwt_token
) {}
