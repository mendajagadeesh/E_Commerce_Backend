package com.jagdev.e_commerceBackend.repository;

import com.jagdev.e_commerceBackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

}
