package com.graphql.GraphqlAuthApplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInput {

    private String username;

    private String password;

    private String fullName;

    private String email;

    // Used for role assignment
    private String jobTitle;
}