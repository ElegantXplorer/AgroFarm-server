package com.cts.agrofarmingstore.controller;

import com.cts.agrofarmingstore.model.Order;
import com.cts.agrofarmingstore.model.User;
import com.cts.agrofarmingstore.service.OrderService;
import com.cts.agrofarmingstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serviceImpl.orderServiceImpl;
import serviceImpl.userServiceImpl;

import java.util.List;

// Controller class for handling Order related requests
@CrossOrigin(value = "http://localhost:4200/", maxAge = 3600)
@RestController
@RequestMapping("/orders")
public class OrderController {
    // creating instance of orderServiceImpl interface
    @Autowired
    private orderServiceImpl orderService;

    //// creating instance of userServiceImpl interface
    @Autowired
    private userServiceImpl userService;

    //fetching all orders
    @GetMapping("/allOrders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orderList = orderService.getAllOrders();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    //fetching orders by Order id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //placing order by user id
    @GetMapping("/add/{id}")
    public Order createOrder(@PathVariable Long id) {
        Order order = orderService.placeOrder(id);
        User user = userService.getUserById(id);
        user.setTotalOrder((user.getTotalOrder() != 0 ? user.getTotalOrder() : 0) + 1);
        userService.updateUser(user);
        return order;
    }

    //cancelling order
    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        return orderService.removeOrder(id);
    }

    //calculating discount based on quantity
    @GetMapping("/order/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


}
