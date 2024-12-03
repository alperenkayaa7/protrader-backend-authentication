package com.protrader.authentication.controller;

import com.protrader.authentication.entity.UserEntity;
import com.protrader.authentication.repository.UserRepository;
import com.protrader.authentication.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationController(UserRepository userRepository,
                                    BCryptPasswordEncoder passwordEncoder,
                                    JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserEntity userEntity) {
        if (userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserEntity userEntity) {
        Optional<UserEntity> existingUser = (userRepository.findByEmail(userEntity.getEmail()));
        if (existingUser.isPresent() && passwordEncoder.matches(userEntity.getPassword(),existingUser.get().getPassword())) {
            String token = jwtUtil.generateToken(userEntity.getEmail());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestBody String token) {
        try {
            boolean isValid = jwtUtil.validateToken(token);
            return ResponseEntity.ok(isValid);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserEntity userEntity) {
        Optional<UserEntity> existingUserOptional = userRepository.findById(userEntity.getId());

        if (existingUserOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        UserEntity existingUser = existingUserOptional.get();
        existingUser.setEmail(userEntity.getEmail());
        existingUser.setPassword(userEntity.getPassword());
        userRepository.save(existingUser);

        return ResponseEntity.ok("User updated successfully");
    }

}