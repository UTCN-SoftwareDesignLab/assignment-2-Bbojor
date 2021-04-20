package com.example.assignment2.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Email(message = "Not a valid email")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Length(min = 5, message = "Password must contain at least 5 characters")
    private String password;

    private Set<String> roles;
}
