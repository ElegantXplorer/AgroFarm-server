package com.cts.agrofarmingstore.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

//Cart Model class having all data's related to Cart history and defined relationships with other model class
@Data
@Entity
@Table(name = "cart")
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private double price;

    // entity relationship with User Class
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //entity relationship with Product Class
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Cart(int quantity, User user, Product product) {
        this.quantity = quantity;
        this.user = user;
        this.product = product;
    }
}
