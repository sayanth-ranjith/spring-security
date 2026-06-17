package com.example.spring_security_demo.demo.auth.jwt;

import com.example.spring_security_demo.demo.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Token Validator Filter for validating incoming HTTP requests with JWT tokens.
 *
 * <p>This filter intercepts HTTP requests and validates the presence of a JWT token
 * in the "INTENDED_TOKEN" header. If the token is missing, an unauthorized (401) response
 * is returned to the client with appropriate error details in JSON format.
 *
 * <p>The filter is excluded from processing requests to the "/generate/app" path and its
 * sub-paths, allowing public access to token generation endpoints without authentication.
 *
 * <p><b>Key Features:</b>
 * <ul>
 *   <li>Validates presence of JWT token in request headers</li>
 *   <li>Returns structured JSON error responses on validation failure</li>
 *   <li>Skips validation for public endpoints under /generate/app/**</li>
 *   <li>Implements Spring's OncePerRequestFilter to ensure single execution per request</li>
 * </ul>
 *
 * <p><b>Expected Header:</b>
 * <pre>
 *   INTENDED_TOKEN: &lt;JWT_TOKEN_VALUE&gt;
 * </pre>
 *
 * <p><b>Error Response Example:</b>
 * <pre>
 * HTTP/1.1 401 Unauthorized
 * Content-Type: application/json
 *
 * {
 *   "status": 401,
 *   "error": "Unauthorized",
 *   "message": "Invalid scenario: Token should not be null to access this request."
 * }
 * </pre>
 *
 * @author Sayanth P V
 * @version 1.0
 * @since 1.0
 */
@Component("jwtTokenValidatorFilter")
@Slf4j
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        // Skip filtering for /generate/app/** paths
        return requestPath.startsWith("/generate/app/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader("INTENDED_TOKEN");
            if (token == null) {
                throw new IllegalArgumentException("Invalid scenario: Token should not be null to access this request.");
            }
            String username = jwtService.extractUserName(token);
            boolean valid = jwtService.verifyToken(token, username);
            if (!valid) {
                throw new IllegalArgumentException("Invalid token");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
            handleException(response, e);
        }
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", e.getMessage());

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }


}
