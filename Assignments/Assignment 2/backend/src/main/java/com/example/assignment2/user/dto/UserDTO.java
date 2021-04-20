package com.example.assignment2.user.dto;

import com.example.assignment2.user.model.ERole;
import com.example.assignment2.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "Must specify username")
    private String username;

    @Email(message = "Not a valid email")
    private String email;

    @Builder.Default
    private Set<String> roles = new HashSet<>();
}
