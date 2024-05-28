package com.cts.agrofarmingstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Order Items Model class having all data's related to Order history and defined relationships with other model class
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderItems")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;

    private double price;

    //entity relationship with Order Class
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

//    entity relationship with Product Class
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    // Creating constructor of some variables that we need in future for service class
    public OrderItems(int quantity, double price, Order order, Product product) {
        super();
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.product = product;
    }
}
