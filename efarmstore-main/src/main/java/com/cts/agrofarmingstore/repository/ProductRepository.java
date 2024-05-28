package com.cts.agrofarmingstore.repository;

import com.cts.agrofarmingstore.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository class for CRUD operations in Product Class
@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Generating sql query to fetch products based on description
    @Query("SELECT p FROM Product p WHERE p.description LIKE %:description%")
    List<Product> findProductsBasedOnDescription(String description);

    // Generating sql query to fetch products based on category
    @Query("SELECT p FROM Product p WHERE p.category LIKE :category%")
    List<Product> findProductsBasedOnCategory(String category);

    // Generating sql query to fetch products based on product name
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    List<Product> findProductsBasedOnName(String name);

    // Generating sql query to fetch products based on price range
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    List<Product> findProductsBasedOnPriceRange(double price1, double price2);
}
