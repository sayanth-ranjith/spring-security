package com.example.spring_security_demo.demo.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserCredDto {
    private @NonNull String username;
    private @NonNull String password;
    private @NonNull String roles;
}
