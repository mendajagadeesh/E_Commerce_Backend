package com.jagdev.e_commerceBackend.repository;

import com.jagdev.e_commerceBackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
