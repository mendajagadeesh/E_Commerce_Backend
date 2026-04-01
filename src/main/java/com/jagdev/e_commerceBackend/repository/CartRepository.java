package com.jagdev.e_commerceBackend.repository;

import com.jagdev.e_commerceBackend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
