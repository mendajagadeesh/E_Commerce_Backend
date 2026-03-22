package com.jagdev.e_commerceBackend.request_dto;

import com.jagdev.e_commerceBackend.model.Category;
import com.jagdev.e_commerceBackend.model.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequestDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;


}
