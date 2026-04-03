package org.example.t10b.controller;


import jakarta.validation.Valid;
import org.example.t10b.dto.Request.LoginRequest;
import org.example.t10b.dto.Request.UserRequest;
import org.example.t10b.security.JwtProvider;
import org.example.t10b.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider,
                          UserDetailsService userDetailsService,
                          UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest request) {
        userService.register(request);
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
        String token = jwtProvider.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(java.util.Map.of(
                        "message", "User registered successfully",
                        "token", token
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword())
        );
        UserDetails user = userDetailsService.loadUserByUsername(request.getLogin());
        return ResponseEntity.ok(java.util.Map.of("token", jwtProvider.generateToken(user)));
    }
}