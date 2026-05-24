package com.graphql.GraphqlAuthApplication.service;

import com.graphql.GraphqlAuthApplication.exception.BadRequestException;
import com.graphql.GraphqlAuthApplication.exception.ResourceNotFoundException;
import com.graphql.GraphqlAuthApplication.exception.UnauthorizedException;
import com.graphql.GraphqlAuthApplication.model.*;
import com.graphql.GraphqlAuthApplication.security.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private final List<User> users = new ArrayList<>();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostConstruct
    public void initUsers() {
        users.add(
                new User(
                        "USR001",
                        "surya",
                        passwordEncoder.encode("password123"),
                        "Surya Kumar",
                        "surya@gmail.com",
                        Role.ADMIN,
                        "ENGINEER"
                )
        );
        users.add(
                new User(
                        "USR002",
                        "subal",
                        passwordEncoder.encode("password123"),
                        "Subal Raj",
                        "subal@gmail.com",
                        Role.USER,
                        "ASSOCIATE"
                )
        );

        users.add(
                new User(
                        "USR003",
                        "ramani",
                        passwordEncoder.encode("password123"),
                        "Ramani Devi",
                        "ramani@gmail.com",
                        Role.USER,
                        "ASSOCIATE"
                )
        );

        users.add(
                new User(
                        "USR004",
                        "monisha",
                        passwordEncoder.encode("password123"),
                        "Monisha Priya",
                        "monisha@gmail.com",
                        Role.ADMIN,
                        "ENGINEER"
                )
        );

        users.add(
                new User(
                        "USR005",
                        "venkata",
                        passwordEncoder.encode("password123"),
                        "Venkata Ramana",
                        "venkata@gmail.com",
                        Role.ADMIN,
                        "ENGINEER"
                )
        );

        users.add(
                new User(
                        "USR006",
                        "likitha",
                        passwordEncoder.encode("password123"),
                        "Likitha Reddy",
                        "likitha@gmail.com",
                        Role.USER,
                        "ASSOCIATE"
                )
        );

        users.add(
                new User(
                        "USR007",
                        "shamugavel",
                        passwordEncoder.encode("password123"),
                        "Shamugavel Murugan",
                        "shamugavel@gmail.com",
                        Role.ADMIN,
                        "ENGINEER"
                )
        );

        users.add(
                new User(
                        "USR008",
                        "sri",
                        passwordEncoder.encode("password123"),
                        "Sri Lakshmi",
                        "sri@gmail.com",
                        Role.USER,
                        "ASSOCIATE"
                )
        );

        users.add(
                new User(
                        "USR009",
                        "syamaladevi",
                        passwordEncoder.encode("password123"),
                        "Syamaladevi K",
                        "syamanadevi@gmail.com",
                        Role.USER,
                        "ASSOCIATE"
                )
        );

        users.add(
                new User(
                        "USR010",
                        "gokhila",
                        passwordEncoder.encode("password123"),
                        "Gokhila Sri",
                        "gokhila@gmail.com",
                        Role.ADMIN,
                        "ENGINEER"
                )
        );

        users.add(
                new User(
                        "USR011",
                        "rehna",
                        passwordEncoder.encode("password123"),
                        "Rehna Begum",
                        "rehna@gmail.com",
                        Role.USER,
                        "ASSOCIATE"
                )
        );

        users.add(
                new User(
                        "USR012",
                        "abdul",
                        passwordEncoder.encode("password123"),
                        "Abdul Rahman",
                        "abdul@gmail.com",
                        Role.ADMIN,
                        "ENGINEER"
                )
        );
    }


    public User register(RegisterInput input) {
        boolean exists = users.stream()
                .anyMatch(user ->
                        user.getUsername()
                                .equalsIgnoreCase(input.getUsername()));
        if (exists) {
            throw new BadRequestException("Username already exists");
        }

        Role role;
        if ("ENGINEER".equalsIgnoreCase(input.getJobTitle())) {
            role = Role.ADMIN;
        } else {
            role = Role.USER;
        }
        String userId = "USR" + (users.size() + 1);
        User user = new User();
        user.setId(userId);
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setJobTitle(input.getJobTitle());
        user.setRole(role);
        users.add(user);
        return user;
    }

    public AuthPayload login(LoginInput input) {
        User user = users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(input.getUsername()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Invalid username"));
        boolean passwordMatches = passwordEncoder.matches(input.getPassword(), user.getPassword());
        if (!passwordMatches) {
            throw new UnauthorizedException("Invalid password");
        }
        String token = jwtUtil.generateToken(user);
        return new AuthPayload(
                token,
                user
        );
    }

    public User getUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<User> getUsers() {
        return users;
    }
}
