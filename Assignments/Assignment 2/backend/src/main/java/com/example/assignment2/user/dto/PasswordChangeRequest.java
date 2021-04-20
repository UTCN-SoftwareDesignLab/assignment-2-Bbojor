package com.example.assignment2.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordChangeRequest {

    @Length(min = 5, message = "Password must have at least 5 characters")
    private String password;
}
