package com.cts.agrofarmingstore.repository;

import com.cts.agrofarmingstore.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repository class for CRUD operations in User Class
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmailId(String email);
}
