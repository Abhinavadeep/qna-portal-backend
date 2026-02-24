package com.Spring.qna_project.controller;

import com.Spring.qna_project.dto.RegisterRequest;
import com.Spring.qna_project.model.User;
import com.Spring.qna_project.repo.UserRepo;
import com.Spring.qna_project.dto.LoginRequest;
import com.Spring.qna_project.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 🔥 NEW CHECKS
        if (!"ACTIVE".equalsIgnoreCase(user.getAccountStatus())) {
            throw new RuntimeException("Account is inactive");
        }

        if ("ADMIN".equalsIgnoreCase(user.getRole()) &&
                !"APPROVED".equalsIgnoreCase(user.getApprovalStatus())) {
            throw new RuntimeException("Admin approval pending");
        }

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(token);
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {

        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAccountStatus("ACTIVE");

        if ("ADMIN".equalsIgnoreCase(request.getRole())) {
            user.setRole("USER");   // First create as USER
            user.setApprovalStatus("PENDING");
        } else {
            user.setRole("USER");
            user.setApprovalStatus("NONE");
        }

        userRepo.save(user);

        return ResponseEntity.ok("User registered successfully");
    }


}
