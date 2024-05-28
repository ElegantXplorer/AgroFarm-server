package com.cts.agrofarmingstore.repository;

import com.cts.agrofarmingstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository class for CRUD operations in Order Class
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Method for fetching Order based on order id from database
    List<Order> findByUserId(Long userId);
}
