package com.jagdev.e_commerceBackend.repository;

import com.jagdev.e_commerceBackend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
	List<Image> findByProductId(Long productId);
}
