package com.example.assignment2;

import com.example.assignment2.book.BookRepository;
import com.example.assignment2.user.RoleRepository;
import com.example.assignment2.user.UserRepository;
import com.example.assignment2.security.dto.SignupRequest;
import com.example.assignment2.security.AuthService;
import com.example.assignment2.user.model.ERole;
import com.example.assignment2.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final BookRepository bookRepository;

    @Value("${app.bootstrapRoles}")
    private Boolean bootstrapRoles;
    @Value("${app.bootstrapUsers}")
    private Boolean bootstrapUsers;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(bootstrapRoles) {
            bookRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
        }

        if(bootstrapUsers) {
            authService.register(SignupRequest.builder()
                    .email("barbu@email.com")
                    .username("bb")
                    .password("Pass123")
                    .roles(Set.of("ADMIN"))
                    .build());

            authService.register(SignupRequest.builder()
                    .email("employee@email.com")
                    .username("employee")
                    .password("Pass123")
                    .roles(Set.of("EMPLOYEE"))
                    .build());
        }
    }
}
