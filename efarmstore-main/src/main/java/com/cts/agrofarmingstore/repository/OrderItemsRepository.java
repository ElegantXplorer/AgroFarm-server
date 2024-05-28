package com.cts.agrofarmingstore.repository;

import com.cts.agrofarmingstore.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// Repository class for CRUD operations in Order Item's Class
@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

}
