package com.cts.agrofarmingstore.service;


import com.cts.agrofarmingstore.exception.ResourceNotFoundException;
import com.cts.agrofarmingstore.model.*;
import com.cts.agrofarmingstore.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serviceImpl.orderServiceImpl;

import java.time.LocalDate;
import java.util.List;
// Service class containing business logic implementing all methods of orderServiceImpl
@Service
public class OrderService implements orderServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    // Creating instance of order Repository
    @Autowired
    OrderRepository orderRepository;

    // Creating instance of product Repository
    @Autowired
    ProductRepository productRepository;

    // Creating instance of cart Repository
    @Autowired
    CartRepository cartRepository;

    // Creating instance of order items Repository
    @Autowired
    OrderItemsRepository orderItemsRepository;

    // Creating instance of user Repository
    @Autowired
    UserRepository userRepository;

    // Method for fetching all orders
    @Override
    public List<Order> getAllOrders() {
        logger.info("Getting all orders");
        List<Order> orderList = orderRepository.findAll();
        if (orderList.isEmpty()) {
            logger.error("No orders are placed yet");
            throw new ResourceNotFoundException("No orders are placed yet");
        }
        return orderList;
    }

    // Method for fetching orders by Order id

    @Override
    public Order getOrderById(Long id) {
        logger.info("Getting order by ID: " + id);
        try {
            Order order = orderRepository.findById(id).get();
            return order;
        } catch (Exception e) {
            logger.error("Wrong order id : " + id);
            throw new ResourceNotFoundException("Wrong order id : " + id);
        }
    }


    //Method for placing order by user id
    @Override
    public Order placeOrder(Long userId) {
        logger.info("Placing order for user ID: " + userId);

        User user = userRepository.findById(userId).get();
        List<Order> orderList = orderRepository.findAll();

        Order order = new Order();

        List<Cart> cartList = cartRepository.findByUserId(user.getId());
        int quantity = 0;
        double price = 0;

        for (Cart existingCart : cartList) {
            quantity += existingCart.getQuantity();
            price = existingCart.getPrice() + price;
        }


        order.setTotalQuantity(quantity);
        order.setTotalPrice(totalPriceAfterDiscount(quantity, price));


        order.setUser(userRepository.findById(userId).get());
        order.setOrderDate(LocalDate.now());
        order.setStatus("Success");
        Order order1 = orderRepository.save(order);

        for (Cart existingCart : cartList) {
            OrderItems orderItem = new OrderItems(existingCart.getQuantity(), existingCart.getPrice(), order1, existingCart.getProduct());

            Product product = productRepository.findById(existingCart.getProduct().getId()).get();
            product.setStockAvailable(product.getStockAvailable() - existingCart.getQuantity());
            productRepository.save(product);
            orderItemsRepository.save(orderItem);
        }

        cartRepository.deleteByUserId(userId);
        logger.info("Order placed successfully");
        return order1;
    }

    // Method for cancelling order
    @Override
    public String removeOrder(Long id) {
        logger.info("Removing order with ID: " + id);
        Order order = getOrderById(id);
        LocalDate orderedDate = order.getOrderDate();
        LocalDate currentDate = LocalDate.now();
        if (orderedDate.plusDays(1).compareTo(currentDate) < 0) {
            logger.error("Your order cannot be cancelled");
            return "Your order cannot be cancelled";
        } else {
            order.setStatus("Cancelled");
            List<OrderItems> orderItemsList = orderItemsRepository.findAll();
            for (OrderItems e : orderItemsList) {
                if (e.getOrder().getId() == id) {
                    Product product = productRepository.findById(e.getProduct().getId()).get();
                    product.setStockAvailable(e.getQuantity() + product.getStockAvailable());
                }
            }
            orderRepository.save(order);
            logger.info("Your order has been cancelled successfully");
            return "Your order has been cancelled successfully";
        }
    }

    // Method for calculating discount based on quantity
    @Override
    public double totalPriceAfterDiscount(int quantity, double price) {

        logger.info("Calculating total price after discount");

        if (quantity >= 50) {
            price = price * (1 - 0.30);
        } else if (quantity >= 25) {
            price = price * (1 - 0.20);
        } else if (quantity >= 10) {
            price = price * (1 - 0.10);
        } else {
            price = price;
        }
        return price;
    }

    // Method for fetching Orders by user id
    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        logger.info("Getting orders for user ID: " + userId);
        return orderRepository.findByUserId(userId);
    }


}
//    public List<Order> getAllOrders()
//    {
//        List<Order> orderList = orderRepository.findAll();
//        if(orderList.isEmpty())
//        {
//            throw new ResourceNotFoundException("No orders are placed yet");
//        }
//        return orderList;
//    }
//
//    public Order getOrderById(Long id)
//    {
//        try
//        {
//            Order order = orderRepository.findById(id).get();
//            return order;
//        }
//        catch (Exception e)
//        {
//            throw new ResourceNotFoundException("Wrong order id : "+id);
//        }
//    }
//
//
//
//    public Order placeOrder(Long userId)
//    {
//        User user = userRepository.findById(userId).get();
//        List<Order> orderList = orderRepository.findAll();
//
//        Order order = new Order();
//
//        List<Cart> cartList = cartRepository.findByUserId(user.getId());
//        int quantity = 0;
//        double price = 0;
//
//        for(Cart existingCart:cartList)
//        {
//            quantity+=existingCart.getQuantity();
//            price = existingCart.getPrice()+price;
//        }
//
//
//        order.setTotalQuantity(quantity);
//        order.setTotalPrice(totalPriceAfterDiscount(quantity,price));
//
//
//        order.setUser(userRepository.findById(userId).get());
//        order.setOrderDate(LocalDate.now());
//        order.setStatus("Success");
//        Order order1 = orderRepository.save(order);
//
//        for(Cart existingCart:cartList)
//        {
//            OrderItems orderItem = new OrderItems(existingCart.getQuantity(),existingCart.getPrice(),order1,existingCart.getProduct());
//
//            Product product = productRepository.findById(existingCart.getProduct().getId()).get();
//            product.setStockAvailable(product.getStockAvailable()-existingCart.getQuantity());
//            productRepository.save(product);
//            orderItemsRepository.save(orderItem);
//        }
//
//        cartRepository.deleteByUserId(userId);
//        return order1;
//    }
//
//    public String removeOrder(Long id)
//    {
//        Order order = getOrderById(id);
//        LocalDate orderedDate = order.getOrderDate();
//        LocalDate currentDate = LocalDate.now();
//        if(orderedDate.plusDays(1).compareTo(currentDate)<0)
//        {
//            return "Your order cannot be cancelled";
//        }
//        else
//        {
//            order.setStatus("Cancelled");
//            List<OrderItems> orderItemsList = orderItemsRepository.findAll();
//            for(OrderItems e : orderItemsList){
//                if(e.getOrder().getId()==id){
//                    Product product = productRepository.findById(e.getProduct().getId()).get();
//                    product.setStockAvailable(e.getQuantity()+ product.getStockAvailable());
//                }
//            }
//            orderRepository.save(order);
//            return "Your order has been cancelled successfully";
//        }
//    }
//
//    public double totalPriceAfterDiscount(int quantity,double price)
//    {
//
//
//        if(quantity>=50)
//        {
//            price = price*(1-0.30);
//        }
//        else if (quantity>=25)
//        {
//            price = price*(1-0.20);
//        }
//        else if(quantity>=10)
//        {
//            price =price*(1-0.10);
//        }
//        else
//        {
//            price = price;
//        }
//        return price;
//    }
//
//    public List<Order> getOrdersByUserId(Long userId)
//    {
//        return  orderRepository.findByUserId(userId);
//    }
//
//
//}
