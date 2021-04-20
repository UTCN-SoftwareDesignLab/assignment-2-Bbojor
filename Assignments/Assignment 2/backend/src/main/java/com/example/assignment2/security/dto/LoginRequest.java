package com.example.assignment2.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "Must specify username")
    private String username;
    @NotBlank(message = "Must specify password")
    private String password;
}
