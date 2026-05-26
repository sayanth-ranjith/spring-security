package com.example.spring_security_demo.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchDataController {

    @GetMapping("/getData")
    public String get() {
        return "YES GOT HERE AFTER AUTHENTICATION.";
    }
}
