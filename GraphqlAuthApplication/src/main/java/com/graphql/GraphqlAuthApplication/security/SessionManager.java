package com.graphql.GraphqlAuthApplication.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {

    @Autowired
    private JwtUtil jwtUtil;

    public String validateSession(String token) {
        return jwtUtil.extractUsername(token);
    }

    public String getUsername(String token) {
        return jwtUtil.extractUsername(token);
    }


    public String getRole(String token) {
        return jwtUtil.extractRole(token);
    }

    public Claims getClaims(String token) {
        return jwtUtil.validateToken(token);
    }

    public boolean isSessionExpired(String token) {
        return jwtUtil.isTokenExpired(token);
    }
}
