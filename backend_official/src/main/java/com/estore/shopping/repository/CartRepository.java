package com.estore.shopping.repository;

import com.estore.shopping.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // This allows us to find a shopping cart using the User's ID
    Optional<Cart> findByUserId(Long userId);
}