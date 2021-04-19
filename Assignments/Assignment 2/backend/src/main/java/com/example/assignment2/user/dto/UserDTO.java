package com.example.assignment2.user.dto;

import com.example.assignment2.user.model.ERole;
import com.example.assignment2.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;

    @Builder.Default
    private Set<String> roles = new HashSet<>();
}
