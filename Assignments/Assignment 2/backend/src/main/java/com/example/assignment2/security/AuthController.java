package com.example.assignment2.security;

import com.example.assignment2.security.dto.JwtResponse;
import com.example.assignment2.security.dto.LoginRequest;
import com.example.assignment2.security.dto.SignupRequest;
import com.example.assignment2.user.dto.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.assignment2.UrlMapping.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping(SIGN_IN)
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl principal = (UserDetailsImpl)authentication.getPrincipal();
        List<String> roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return ResponseEntity.ok(
                JwtResponse.builder()
                .token(jwt)
                .id(principal.getId())
                .email(principal.getEmail())
                .username(principal.getUsername())
                .roles(roles)
                .build()
        );
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if(authService.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body("Error: Username already taken!");
        }

        if(authService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Error: Email already has an account associated with it!");
        }

        authService.register(signupRequest);

        return ResponseEntity.ok("User registered successfully!");
    }

}
