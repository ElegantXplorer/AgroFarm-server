package com.cts.agrofarmingstore.controller;

import com.cts.agrofarmingstore.dto.CartDTO;
import com.cts.agrofarmingstore.dto.UpdateCart;
import com.cts.agrofarmingstore.model.Cart;
import com.cts.agrofarmingstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serviceImpl.cartServiceImpl;

import java.util.List;

// Controller class for handling Cart related requests
@CrossOrigin(value = "http://localhost:4200/", maxAge = 3600)
@RestController
@RequestMapping("/carts")
public class CartController {

    // creating instance of cartServiceImpl interface3
    @Autowired
    private cartServiceImpl cartService;


    //fetching all carts
    @GetMapping("/allcarts")
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> cartList = cartService.getAllCarts();
        return new ResponseEntity<>(cartList, HttpStatus.OK);
    }

    //creating carts
    @PostMapping("/add")
    public ResponseEntity<Cart> createCart(@RequestBody CartDTO cartObj) {
        Cart cart = cartService.createCart(cartObj);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    //removing carts
    @DeleteMapping("/delete/{id}")
    public void removeCartItem(@PathVariable Long id) {
        cartService.removeCart(id);
    }

    //updating carts
    @PutMapping("/update/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody UpdateCart updateCart) {
        Cart cartObj = cartService.updateCart(id, updateCart);
        return new ResponseEntity<>(cartObj, HttpStatus.OK);
    }

    //fetching carts by user id
    @GetMapping("/{userId}")
    public ResponseEntity<List<Cart>> getCartsByUserId(@PathVariable Long userId) {
        List<Cart> carts = cartService.getCartsByUserId(userId);
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }
}
