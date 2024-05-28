package com.cts.agrofarmingstore.service;

import com.cts.agrofarmingstore.dto.CartDTO;
import com.cts.agrofarmingstore.dto.UpdateCart;
import com.cts.agrofarmingstore.exception.ResourceNotFoundException;
import com.cts.agrofarmingstore.model.Cart;
import com.cts.agrofarmingstore.model.Product;
import com.cts.agrofarmingstore.model.User;
import com.cts.agrofarmingstore.repository.CartRepository;
import com.cts.agrofarmingstore.repository.ProductRepository;
import com.cts.agrofarmingstore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serviceImpl.cartServiceImpl;

import java.util.List;
// Service class containing business logic implementing all methods of cartServiceImpl
@Service
public class CartService implements cartServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    // Creating instance of user Repository
    @Autowired
    UserRepository userRepository;

    // Creating instance of cart Repository
    @Autowired
    private CartRepository cartRepository;

    // Creating instance of product Repository
    @Autowired
    private ProductRepository productRepository;

    // Method for fetching all carts
    @Override
    public List<Cart> getAllCarts() {
        List<Cart> cartList = cartRepository.findAll();
        if (cartList.isEmpty()) {
            logger.error("No items are selected");
            throw new ResourceNotFoundException("No items are selected");
        }
        return cartList;
    }


    // Methods for creating carts
    @Override
    public Cart createCart(CartDTO cartdto) {
        logger.info("Creating cart with product ID: " + cartdto.getProductId() + " and user ID: " + cartdto.getUserId());

        try {
            Product product = productRepository.findById(cartdto.getProductId()).get();
            User user = userRepository.findById(cartdto.getUserId()).get();
            Cart cartObj = new Cart(cartdto.getQuantity(), user, product);

            List<Cart> cartList = cartRepository.findAll();

            if (cartList != null) {
                for (Cart existingCart : cartList) {
                    if (existingCart.getUser().getId() == cartObj.getUser().getId()) {
                        if (existingCart.getProduct().getId() == cartObj.getProduct().getId()) {
                            double price = existingCart.getPrice();

                            double productPrice = product.getPrice();
                            existingCart.setPrice((productPrice * cartObj.getQuantity()) + price);
                            existingCart.setQuantity(cartObj.getQuantity() + existingCart.getQuantity());
                            logger.info("Updating existing cart");
                            return cartRepository.save(existingCart);
                        }
                    }
                }
            }

            double price = product.getPrice();
            cartObj.setPrice(price * cartObj.getQuantity());

            cartObj.setProduct(product);
            cartObj.setUser(userRepository.findById(cartObj.getUser().getId()).get());
            cartObj.setUser(user);
            Cart cart = cartRepository.save(cartObj);
            logger.info("Created new cart");
            return cart;

        } catch (Exception e) {
            logger.error("The Selected product does not exist in the database");
            throw new ResourceNotFoundException("The Selected product does not exist in the database");
        }
    }

    // Method for removing carts
    @Override
    public void removeCart(Long id) {
        logger.info("Removing cart with ID: " + id);
        try {
            Cart cart = cartRepository.findById(id).get();
            cartRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Something went wrong while removing cart");
            throw new ResourceNotFoundException("Something went wrong");
        }
    }

    // Methods for updating carts

    @Override
    public Cart updateCart(Long id, UpdateCart updateCart) {
        logger.info("Updating cart with ID: " + id);
        try {
            Cart cartObj = cartRepository.findById(id).get();
            Product product = productRepository.findById(updateCart.getProductId()).get();
            cartObj.setQuantity(updateCart.getQuantity());
            double productPrice = product.getPrice();
            cartObj.setProduct(product);
            cartObj.setPrice(productPrice * updateCart.getQuantity());
            logger.info("Updated cart");
            return cartRepository.save(cartObj);
        } catch (Exception e) {
            logger.error("Check your input");
            throw new ResourceNotFoundException("Check your input");
        }
    }


    // Method for fetching carts by user id
    @Override
    public List<Cart> getCartsByUserId(Long userId) {
        logger.info("Getting carts by user ID: " + userId);
        return cartRepository.findByUserId(userId);
    }

}
//    public List<Cart> getAllCarts()
//    {
//        List<Cart> cartList = cartRepository.findAll();
//        if(cartList.isEmpty())
//        {
//            throw new ResourceNotFoundException("No items are selected");
//        }
//        return cartList;
//    }
//
//
//
//    public Cart createCart(CartDTO cartdto)
//    {
//
//        try
//        {
//            Product product = productRepository.findById(cartdto.getProductId()).get();
//            User user = userRepository.findById(cartdto.getUserId()).get();
//            Cart cartObj = new Cart(cartdto.getQuantity(), user, product);
//
//            List<Cart> cartList = cartRepository.findAll();
//
//            if (cartList != null) {
//                for (Cart existingCart : cartList) {
//                    if (existingCart.getUser().getId() == cartObj.getUser().getId()) {
//                        if (existingCart.getProduct().getId() == cartObj.getProduct().getId()) {
//                            double price = existingCart.getPrice();
//
//                            double productPrice = product.getPrice();
//                            existingCart.setPrice((productPrice * cartObj.getQuantity()) + price);
//                            existingCart.setQuantity(cartObj.getQuantity() + existingCart.getQuantity());
//                            return cartRepository.save(existingCart);
//                        }
//                    }
//                }
//            }
//
//            double price = product.getPrice();
//            cartObj.setPrice(price * cartObj.getQuantity());
//
//            cartObj.setProduct(product);
//            cartObj.setUser(userRepository.findById(cartObj.getUser().getId()).get());
//            cartObj.setUser(user);
//            Cart cart = cartRepository.save(cartObj);
//
//            return cart;
//
//        }catch (Exception e)
//        {
//            throw  new ResourceNotFoundException("The Selected product does not exist in the database");
//        }
//    }
//
//    public void removeCart(Long id)
//    {
//        try
//        {
//            Cart cart = cartRepository.findById(id).get();
//            cartRepository.deleteById(id);
//        }
//        catch (Exception e)
//        {
//            throw new ResourceNotFoundException("Something went wrong");
//        }
//    }
//
//    public Cart updateCart(Long id, UpdateCart updateCart)
//    {
//        try {
//            Cart cartObj = cartRepository.findById(id).get();
//            Product product = productRepository.findById(updateCart.getProductId()).get();
//            cartObj.setQuantity(updateCart.getQuantity());
//            double productPrice = product.getPrice();
//            cartObj.setProduct(product);
//            cartObj.setPrice(productPrice * updateCart.getQuantity());
//            return cartRepository.save(cartObj);
//        }catch (Exception e){
//            throw new ResourceNotFoundException("Check your input");
//        }
//    }
//
//    public List<Cart> getCartsByUserId(Long userId)
//    {
//        return  cartRepository.findByUserId(userId);
//    }
//
//}