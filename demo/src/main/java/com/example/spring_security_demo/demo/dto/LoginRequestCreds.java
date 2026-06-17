package com.example.spring_security_demo.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestCreds {
    private String loginComment;
    private String jwtToken;
    private String username;
}
