# GraphQL Authentication & Authorization using Spring Boot

A production-style Spring Boot GraphQL application that demonstrates secure authentication, authorization, JWT-based access control, and protected GraphQL operations using Spring GraphQL and Spring Security.

## Overview

This project shows how to build a secure GraphQL API with Spring Boot using JWT for authentication and role-based access control for authorization. It includes user registration, login, token generation and validation, protected queries, password encryption with BCrypt, exception handling, and request header propagation into the GraphQL context.

The application is designed as a clean learning project for developers who want to understand how authentication and authorization work in a GraphQL-based Spring Boot backend while keeping the structure close to real-world service design.

## Features

### Authentication
- User registration
- User login
- JWT token generation
- JWT token validation
- Session validation
- Authorization header validation

### Authorization
- Role-based access control (RBAC)
- ADMIN and USER roles
- Protected GraphQL queries
- Access restriction based on user role

### Security
- BCrypt password encryption
- Token expiration validation
- Session verification
- Context-based authentication for GraphQL resolvers
- Header interceptor configuration for GraphQL context access

### GraphQL
- Query resolvers
- Mutation resolvers
- Protected resolver methods
- Context-aware request handling
- Global exception handling for GraphQL errors


## GraphQL Schema

### Role Enum

```graphql
enum Role {
  USER
  ADMIN
}
```

### Queries

```graphql
type Query {
  me: User!
  products: [Product!]!
}
```

### Mutations

```graphql
type Mutation {
  register(input: RegisterInput!): User!
  login(input: LoginInput!): AuthPayload!
}
```

### Types

```graphql
type User {
  id: ID!
  username: String!
  fullName: String!
  email: String!
  role: Role!
  jobTitle: String!
}

type Product {
  id: ID!
  name: String!
  category: String!
  price: Float!
  stock: Int!
}

type AuthPayload {
  token: String!
  user: User!
}
```

### Inputs

```graphql
input RegisterInput {
  username: String!
  password: String!
  fullName: String!
  email: String!
  jobTitle: String!
}

input LoginInput {
  username: String!
  password: String!
}
```

## Security Flow

### 1. Registration
- Client sends registration input
- Password is encrypted using BCrypt
- A role is assigned based on application logic
- User details are stored for authentication and access control

### 2. Login
- Client submits username and password
- Credentials are validated
- JWT token is generated after successful authentication
- Token is returned in the GraphQL response

### 3. Protected Requests
- Client sends the JWT token in the `Authorization` header
- Header is exposed to GraphQL context through an interceptor
- Token is validated before protected data is returned
- User role is checked before allowing access to restricted operations

## Authorization Rules

| Role | Access |
|---|---|
| ADMIN | `me`, `products` |
| USER | `me` |
| USER | `products` denied |

## GraphQL Header Configuration

The following interceptor exposes the `Authorization` header to the GraphQL context so it can be accessed in resolver methods using `@ContextValue("Authorization")`.

```java
@Configuration
public class GraphQlConfig {

    @Bean
    WebGraphQlInterceptor headerInterceptor() {
        return HttpRequestHeaderInterceptor.builder()
                .headerNames("Authorization")
                .build();
    }
}
```

Example resolver parameter:

```java
@ContextValue("Authorization") String authHeader
```

## Sample GraphQL Operations

### Register User

```graphql
mutation {
  register(
    input: {
      username: "karthik"
      password: "password123"
      fullName: "Karthik Raja"
      email: "karthik@gmail.com"
      jobTitle: "ENGINEER"
    }
  ) {
    id
    username
    role
  }
}
```

### Login

```graphql
mutation {
  login(
    input: {
      username: "surya"
      password: "password123"
    }
  ) {
    token
    user {
      username
      role
    }
  }
}
```

### Get Current User

> Add the JWT token in the request header:
>
> `Authorization: Bearer <your_token>`

```graphql
query {
  me {
    username
    role
    email
    fullName
    jobTitle
  }
}
```

### Get Products

> Add the JWT token in the request header:
>
> `Authorization: Bearer <your_token>`

```graphql
query {
  products {
    id
    name
    category
    price
    stock
  }
}
```

## Expected Scenarios

### Successful Operations
- Register a new user with valid input
- Login with valid credentials
- Access `me` with a valid token
- Access `products` with an ADMIN token

### Validation and Authorization Errors
- Duplicate username
- Invalid username
- Invalid password
- Missing authorization header
- Invalid or expired token
- USER attempting to access ADMIN-only resources

## Example Error Messages

```text
Username already exists
Invalid username
Invalid password
Missing Authorization Header
Access Denied
```

## Why This Project Is Useful

This project is a strong reference for developers learning how to secure GraphQL APIs in Spring Boot. It demonstrates how authentication and authorization fit into resolver logic, how JWT can be integrated into GraphQL request handling, and how role-based access rules can be enforced cleanly at the application level.
