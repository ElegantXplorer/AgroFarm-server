package com.cts.agrofarmingstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//Order Model class having all data's related to Order Placing and defined relationships with other model class
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate orderDate;
    private int totalQuantity;
    private double totalPrice;
    private String status;

    //entity relationship with User Class
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //entity relationship with Order Item's Class
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItems> orderItems = new HashSet<>();


}
