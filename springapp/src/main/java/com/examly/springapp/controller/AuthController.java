package com.examly.springapp.controller;

import com.examly.springapp.dto.*;
import com.examly.springapp.model.*;
import com.examly.springapp.repository.*;
import com.examly.springapp.security.JwtTokenProvider;
import com.examly.springapp.service.CustomUserDetails;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authManager,
                          UserRepository userRepo,
                          RoleRepository roleRepo,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider tokenProvider) {
        this.authManager = authManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = request.getRole().equalsIgnoreCase("librarian")
                ? roleRepo.findByName(RoleType.ROLE_LIBRARIAN).orElseThrow()
                : roleRepo.findByName(RoleType.ROLE_MEMBER).orElseThrow();

        user.setRoles(Set.of(role));
        userRepo.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

   @PostMapping("/signin")
public ResponseEntity<?> signin(@RequestBody LoginRequest request) {

    try {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
            )
        );

        CustomUserDetails userDetails =
                (CustomUserDetails) auth.getPrincipal();

        String token = tokenProvider.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(
            new JwtResponse(
                token,
                userDetails.getUsername(),
                userDetails.getAuthorities().stream()
                    .map(a -> a.getAuthority())
                    .collect(Collectors.toList())
            )
        );

    } catch (AuthenticationException e) {
        return ResponseEntity
                .status(401)
                .body("Invalid username or password");
    }
}

}
