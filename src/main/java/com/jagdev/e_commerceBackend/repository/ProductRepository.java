package com.jagdev.e_commerceBackend.repository;
import com.jagdev.e_commerceBackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByNameIgnoreCase(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandIgnoreCaseAndNameIgnoreCase(String brand, String name);

    boolean existsByNameAndBrand(String name, String brand);
}
