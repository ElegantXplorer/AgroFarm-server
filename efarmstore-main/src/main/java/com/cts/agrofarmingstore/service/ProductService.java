package com.cts.agrofarmingstore.service;

import com.cts.agrofarmingstore.exception.ResourceNotFoundException;
import com.cts.agrofarmingstore.model.Product;
import com.cts.agrofarmingstore.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serviceImpl.productServiceImpl;

import java.util.List;
import java.util.Optional;
// Service class containing business logic implementing all methods of productServiceImpl
@Service
public class ProductService implements productServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    // Creating instance of product Repository
    @Autowired
    private ProductRepository productRepository;

    // Method for fetching all Products
    @Override
    public List<Product> getAllProducts() {
        logger.info("Getting all products");
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {logger.error("No products are present");


            throw new ResourceNotFoundException("No products are present");
        }
        return productList;
    }

    //Method for fetching Products by product id

    @Override
    public Product getProductById(Long id) {
        logger.info("Getting product by ID: " + id);
        Optional<Product> product = productRepository.findById(id);
        try {
            return product.get();
        } catch (Exception e) {
            logger.error("The product you are searching for does not exist");
            throw new ResourceNotFoundException("The product you are searching for does not exist");
        }
    }

    // Method for adding product
    @Override
    public Product addProduct(Product product) {
        logger.info("Adding product");
        if (product != null) {
            return productRepository.save(product);
        } else {
            logger.error("All the product details are not given");
            throw new ResourceNotFoundException("All the product details are not given");
        }


    }

    // Method for deleting product
    @Override
    public void deleteProduct(Long id) {
        logger.info("Deleting product with ID: " + id);
        try {
            productRepository.findById(id).get();
            productRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("The product with id :"+id+" doesn't exist.");
            throw new ResourceNotFoundException("The product with id :" + id + " doesn't exist.");
        }
    }

    // Method for updating Product
    @Override
    public Product updateProduct(Product product) {
        logger.info("Updating product with ID: " + product.getId());
        try {
            Product product1 = productRepository.findById(product.getId()).get();
            product1.setStockAvailable(product.getStockAvailable());
            product1.setDescription(product.getDescription());
            product1.setPrice(product.getPrice());
            product1.setName(product.getName());
            product1.setCategory(product.getCategory());
            product1.setYear(product.getYear());
            return productRepository.save(product1);
        } catch (Exception e) {
            logger.error("The product you are trying to update doesn't exist");
            throw new ResourceNotFoundException("The product you are trying to update doesn't exist");
        }
    }

    // Method for searching product based on Description
    @Override
    public List<Product> searchProductsBasedOnDescription(String description) {
        logger.info("Searching products based on description: " + description);
        List<Product> productList = productRepository.findProductsBasedOnDescription(description);
        if (productList.isEmpty()) {
            logger.error("The product you are searching of the description "+description+" are not present");
            throw new ResourceNotFoundException("The product you are searching of the description " + description + " are not present");
        }
        return productList;
    }

    //Method for searching product based on Category
    @Override
    public List<Product> searchProductsBasedOnCategory(String category) {
        logger.info("Searching products based on category: " + category);

        List<Product> productList = productRepository.findProductsBasedOnCategory(category);
        if (productList.isEmpty()) {
            logger.error("There are no product for the category "+category);
            throw new ResourceNotFoundException("There are no product for the category " + category);
        }
        return productList;
    }

    //Method for searching product based on Product Name
    @Override
    public List<Product> searchProductsBasedOnName(String name) {
        logger.info("Searching products based on name: " + name);
        List<Product> productList = productRepository.findProductsBasedOnName(name);
        if (productList.isEmpty()) {
            logger.error("There are no product based on the name "+name);
            throw new ResourceNotFoundException("There are no product based on the name " + name);
        }
        return productList;
    }

    // Method for searching product based on Price Range
    @Override
    public List<Product> searchProductsBasedOnPriceRange(double price1, double price2) {
        logger.info("Searching products based on price range: " + price1 + " to " + price2);
        List<Product> productList = productRepository.findProductsBasedOnPriceRange(price1, price2);
        if (productList.isEmpty()) {
            logger.error("There are no product in the price range you are searching for");
            throw new ResourceNotFoundException("There are no product in the price range you are searching for");
        }
        return productList;
    }

}
