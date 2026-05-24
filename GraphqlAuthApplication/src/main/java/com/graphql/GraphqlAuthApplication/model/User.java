package com.graphql.GraphqlAuthApplication.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;

    private String username;

    // BCrypt encrypted password
    private String password;

    private String fullName;

    private String email;

    // USER / ADMIN
    private Role role;

    // ENGINEER / ASSOCIATE
    private String jobTitle;
}