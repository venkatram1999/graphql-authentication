package com.graphql.GraphqlAuthApplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthPayload {

    // JWT token
    private String token;

    // Logged in user
    private User user;
}
