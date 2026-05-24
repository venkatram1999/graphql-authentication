package com.graphql.GraphqlAuthApplication.service;

import com.graphql.GraphqlAuthApplication.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();
    @PostConstruct
    public void initProducts() {

        products.add(
                new Product(
                        "PRD001",
                        "iPhone 15",
                        "Mobile",
                        85000.0,
                        10
                )
        );

        products.add(
                new Product(
                        "PRD002",
                        "Samsung TV",
                        "Electronics",
                        65000.0,
                        5
                )
        );

        products.add(
                new Product(
                        "PRD003",
                        "MacBook Pro",
                        "Laptop",
                        180000.0,
                        3
                )
        );

        products.add(
                new Product(
                        "PRD004",
                        "Sony Headphones",
                        "Accessories",
                        12000.0,
                        15
                )
        );

        products.add(
                new Product(
                        "PRD005",
                        "Dell Monitor",
                        "Computer",
                        25000.0,
                        7
                )
        );

        products.add(
                new Product(
                        "PRD006",
                        "Gaming Mouse",
                        "Accessories",
                        4500.0,
                        18
                )
        );

        products.add(
                new Product(
                        "PRD007",
                        "Apple Watch",
                        "Wearables",
                        42000.0,
                        8
                )
        );

        products.add(
                new Product(
                        "PRD008",
                        "AirPods Pro",
                        "Audio",
                        26000.0,
                        12
                )
        );

        products.add(
                new Product(
                        "PRD009",
                        "iPad Air",
                        "Tablet",
                        70000.0,
                        6
                )
        );

        products.add(
                new Product(
                        "PRD010",
                        "Asus Laptop",
                        "Laptop",
                        95000.0,
                        4
                )
        );
    }

    public List<Product> getProducts() {
        return products;
    }
}
