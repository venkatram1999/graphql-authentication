package com.graphql.GraphqlAuthApplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String id;

    private String name;

    private String category;

    private Double price;

    private Integer stock;
}