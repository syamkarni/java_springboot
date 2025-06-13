package com.accolite.JPAHibernate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    
    private String name;
    private String description;
    private Double price;
    private Integer quantity;

    @ManyToMany(mappedBy = "products")
    private Set<Student> students = new HashSet<>();
}
