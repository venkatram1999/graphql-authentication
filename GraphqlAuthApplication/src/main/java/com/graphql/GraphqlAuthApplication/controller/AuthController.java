package com.graphql.GraphqlAuthApplication.controller;

import com.graphql.GraphqlAuthApplication.exception.UnauthorizedException;
import com.graphql.GraphqlAuthApplication.model.*;
import com.graphql.GraphqlAuthApplication.security.SessionManager;
import com.graphql.GraphqlAuthApplication.service.AuthService;
import com.graphql.GraphqlAuthApplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SessionManager sessionManager;

    @MutationMapping
    public User register(@Argument RegisterInput input) {
        return authService.register(input);
    }

    @MutationMapping
    public AuthPayload login(@Argument LoginInput input) {
        return authService.login(input);
    }


    @QueryMapping
    public User me(@ContextValue("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Missing Authorization Header");
        }
        String token = authHeader.replace("Bearer ", "");
        String username = sessionManager.validateSession(token);
        return authService.getUserByUsername(username);
    }

    @QueryMapping
    public List<Product> products(@ContextValue("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Missing Authorization Header");
        }
        String token = authHeader.replace("Bearer ", "");
        String role = sessionManager.getRole(token);

        if (!role.equals("ADMIN")) {
            throw new UnauthorizedException("Access Denied");
        }
        return productService.getProducts();
    }
}