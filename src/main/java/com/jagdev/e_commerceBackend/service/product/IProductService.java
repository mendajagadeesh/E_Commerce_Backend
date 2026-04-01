package com.jagdev.e_commerceBackend.service.product;

import com.jagdev.e_commerceBackend.Dto.ProductDto;
import com.jagdev.e_commerceBackend.model.Product;
import com.jagdev.e_commerceBackend.request_dto.AddProductRequestDto;
import com.jagdev.e_commerceBackend.request_dto.ProductUpdateRequestDto;
import com.jagdev.e_commerceBackend.service.product.IProductService;
import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequestDto addProductRequestDto);
    Product getProductById(Long id);
    Product updateProductById(ProductUpdateRequestDto dto, Long productId); // ✅ corrected

    //void updateProductById(Long id);

    void deleteProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
    List<ProductDto> getConvertedProducts(List<Product> products);
    ProductDto convertToDto(Product product);
}