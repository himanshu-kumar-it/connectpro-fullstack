
package com.himanshu.connectpro.controller;

import com.himanshu.connectpro.dto.*;
        import com.himanshu.connectpro.entity.User;
import com.himanshu.connectpro.repository.UserRepository;
import com.himanshu.connectpro.security.CustomUserDetails;
import com.himanshu.connectpro.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
        import org.springframework.web.bind.annotation.*;

        import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(new CustomUserDetails(user));

        String role = user.getRoles()
                .stream()
                .findFirst()
                .map(r -> r.getName())
                .orElse("ROLE_USER");

        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .userId(user.getId())
                .email(user.getEmail())
                .role(role)
                .build();

        return ResponseEntity.ok(
                ApiResponse.<LoginResponse>builder()
                        .success(true)
                        .message("Login successful")
                        .data(loginResponse)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}