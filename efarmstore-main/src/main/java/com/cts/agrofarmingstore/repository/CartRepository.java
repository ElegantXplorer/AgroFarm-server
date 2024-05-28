package com.cts.agrofarmingstore.repository;

import com.cts.agrofarmingstore.model.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// Repository class for CRUD operations in Cart Class
@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, Long> {


    //
    List<Cart> findByUserId(Long id);

    void deleteByUserId(Long id);
}
