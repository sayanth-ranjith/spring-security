package com.example.spring_security_demo.demo.controller;

import com.example.spring_security_demo.demo.annotation.GenerateAppRestController;
import com.example.spring_security_demo.demo.auth.JwtService;
import com.example.spring_security_demo.demo.dto.LoginRequestCreds;
import com.example.spring_security_demo.demo.dto.UserCredDto;
import com.example.spring_security_demo.demo.entity.UserCredInfo;
import com.example.spring_security_demo.demo.repo.UserCredRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/generate/app")
@Slf4j
public class LoginController {

    @Autowired
    private UserCredRepo userCredRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginRequestCreds> login(@RequestBody UserCredDto userCredDto) {
        if (userCredDto != null ) {
            Optional<UserCredInfo> user = userCredRepo.findByUsername(userCredDto.getUsername());
            UserCredInfo userCredInfo = user.orElse(null);
            if (userCredInfo == null) {
                throw new RuntimeException("User not found with username: " + userCredDto.getUsername());
            }
            Authentication authentication = authenticateUser(userCredDto);
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateJwtToken(userCredInfo.getUsername());
                log.info("Generated JWT Token: {}", token);
                return ResponseEntity.ok(LoginRequestCreds.builder()
                        .loginComment("Login successful")
                        .jwtToken(token)
                        .username(userCredInfo.getUsername())
                        .build());
            }
        }
        return ResponseEntity.ok(LoginRequestCreds.builder()
                .loginComment("Login failed")
                .build());
    }

    private Authentication authenticateUser(UserCredDto userCredDto) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userCredDto.getUsername(),
                        userCredDto.getPassword()
                ));
    }

}
