package com.jagdev.e_commerceBackend.repository;

import com.jagdev.e_commerceBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
