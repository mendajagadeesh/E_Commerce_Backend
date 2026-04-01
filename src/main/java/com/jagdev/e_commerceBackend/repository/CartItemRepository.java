package com.jagdev.e_commerceBackend.repository;

import com.jagdev.e_commerceBackend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);
}
