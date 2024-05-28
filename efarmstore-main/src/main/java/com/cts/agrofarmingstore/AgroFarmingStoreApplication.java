package com.cts.agrofarmingstore;

import com.cts.agrofarmingstore.model.User;
import com.cts.agrofarmingstore.repository.OrderItemsRepository;
import com.cts.agrofarmingstore.repository.ProductRepository;
import com.cts.agrofarmingstore.service.CartService;
import com.cts.agrofarmingstore.service.OrderService;
import com.cts.agrofarmingstore.service.ProductService;
import com.cts.agrofarmingstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Main Application containing beans
@SpringBootApplication
public class AgroFarmingStoreApplication implements CommandLineRunner {
    // Creating instance of cart service class
    @Autowired
    CartService cartService;

    // Creating instance of product service class
    @Autowired
    ProductService productService;

    // Creating instance of user service class
    @Autowired
    UserService userService;

    // Creating instance of order service class
    @Autowired
    OrderService orderService;

    // Creating instance of cart order items repository
    @Autowired
    OrderItemsRepository orderItemsRepository;

//    // Creating instance of cart Product repository
    @Autowired
    ProductRepository productRepository;

    Logger logger= LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(AgroFarmingStoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        User user = new User(9L,"Harsh","890876578", "harsh@gmail.com","Harshit12","UP","UP","UP",734051, "Home","user");
//        System.out.println(user);


    }
}
