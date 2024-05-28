package com.cts.agrofarmingstore.controller;


import com.cts.agrofarmingstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serviceImpl.productServiceImpl;

import java.util.List;

// Controller class for handling Product related requests
@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/products")
public class ProductController {

    // creating instance of productServiceImpl interface
    @Autowired
    private productServiceImpl productService;

    //fetching all Products
    @GetMapping("/allproducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    //fetching Products by product id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

//    adding product
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product productObj) {
        Product product = productService.addProduct(productObj);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

//    deleting product
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

//    updating Product
    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product productObj) {
        Product product = productService.updateProduct(productObj);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

//    searching product based on Description
    @GetMapping("product/description/{descriptionName}")
    public ResponseEntity<List<Product>> getProductBasedOnDescription(@PathVariable String description) {
        List<Product> productList = productService.searchProductsBasedOnDescription(description);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

//    searching product based on Category
    @GetMapping("product/{category}")
    public ResponseEntity<List<Product>> getProductBasedOnCategory(@PathVariable String category) {
        List<Product> productList = productService.searchProductsBasedOnCategory(category);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

//    searching product based on Product Name
    @GetMapping("product/name/{name}")
    public ResponseEntity<List<Product>> getProductBasedOnName(@PathVariable String name) {
        List<Product> productList = productService.searchProductsBasedOnName(name);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


    //searching product based on Price Range
    @GetMapping("price/{price1}/{price2}")
    public ResponseEntity<List<Product>> getProductBasedOnPriceRange(@PathVariable double price1, @PathVariable double price2) {
        List<Product> productList = productService.searchProductsBasedOnPriceRange(price1, price2);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


}
