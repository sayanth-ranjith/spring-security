package com.example.spring_security_demo.demo.controller;

import com.example.spring_security_demo.demo.dto.UserCredDto;
import com.example.spring_security_demo.demo.entity.UserCredInfo;
import com.example.spring_security_demo.demo.repo.UserCredRepo;
import com.example.spring_security_demo.demo.service.PasswordEncoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/generate/app")
public class BaseLandingController {

    @Autowired
    private UserCredRepo userCredRepo;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Spring Security Demo Application!";
    }

    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestBody UserCredDto userCredDto) {
        UserCredInfo userCredInfo = new UserCredInfo();
        userCredInfo.setPassword(passwordEncoderService.encodePassword(userCredDto.getPassword()));
        userCredInfo.setUsername(userCredDto.getUsername());
        userCredInfo.setRoles(userCredDto.getRoles());
        userCredRepo.save(userCredInfo);
        return new ResponseEntity<>("USER SUCCESFULLY REGISTERED", HttpStatus.OK);
    }

}
