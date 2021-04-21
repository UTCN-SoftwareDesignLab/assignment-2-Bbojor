package com.example.assignment2.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordChangeRequest {

    @NotBlank(message = "Must specify a password")
    @Length(min = 5, message = "Password must have at least 5 characters")
    private String password;
}
