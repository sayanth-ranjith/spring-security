package com.example.spring_security_demo.demo.dto;

import lombok.Data;

@Data
public class UserCredDto {
    private String username;
    private String password;
    private String roles;
}
