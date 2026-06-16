package com.example.spring_security_demo.demo.controller;

import com.example.spring_security_demo.demo.auth.JwtService;
import com.example.spring_security_demo.demo.dto.UserCredDto;
import com.example.spring_security_demo.demo.entity.UserCredInfo;
import com.example.spring_security_demo.demo.repo.UserCredRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Optional;

@RestController
public class LoginController {

    @Autowired
    private UserCredRepo userCredRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public String login(@RequestBody UserCredDto userCredDto) {
        if (userCredDto != null ) {
            Optional<UserCredInfo> user = userCredRepo.findByUsername(userCredDto.getUsername());
            UserCredInfo userCredInfo = user.orElse(null);
            if (userCredInfo == null) {
                throw new RuntimeException("User not found with username: " + userCredDto.getUsername());
            }
            if (passwordEncoder.matches(userCredDto.getPassword(), userCredInfo.getPassword())) {
                String token = jwtService.generateJwtToken(userCredInfo.getUsername());
                System.out.println("Generated JWT Token: " + token);
            }
        }
        return "LOGIN SUCCESSFUL";
    }

}
