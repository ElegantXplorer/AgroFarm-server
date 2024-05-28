package com.cts.agrofarmingstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


//Product Model class having all data's related to Product and defined relationships with other model class
@Data
@Entity
@Table(name = "product")
public class
Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String description;
    private String category;
    private int year;
    private double price;
    private int stockAvailable;

    //entity relationship with Cart Class
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Cart> cart = new HashSet<>();

    //entity relationship with Order Items Class
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderItems> orderItems = new HashSet<>();
}
