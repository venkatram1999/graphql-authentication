package com.graphql.GraphqlAuthApplication.security;

import com.graphql.GraphqlAuthApplication.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public String generateToken(User user) {

        return Jwts.builder()
                // Username stored as subject
                .setSubject(user.getUsername())
                // Custom claims
                .claim("role", user.getRole().getValue())
                .claim("email", user.getEmail())
                // Token timestamps
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                // Sign token
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }


    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return validateToken(token)
                .getSubject();
    }

    public String extractRole(String token) {
        return validateToken(token)
                .get("role", String.class);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = validateToken(token).getExpiration();
        return expirationDate.before(new Date());
    }
}
