package com.graphql.GraphqlAuthApplication.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.support.HttpRequestHeaderInterceptor;

@Configuration
public class GraphQlConfig {
    @Bean
    WebGraphQlInterceptor headerInterceptor() {
        return HttpRequestHeaderInterceptor.builder()
                .mapHeader("Authorization")
                .build();
    }
}
