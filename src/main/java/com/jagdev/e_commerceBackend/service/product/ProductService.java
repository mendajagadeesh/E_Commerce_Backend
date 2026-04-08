package com.jagdev.e_commerceBackend.service.product;

import com.jagdev.e_commerceBackend.Dto.ImageDto;
import com.jagdev.e_commerceBackend.Dto.ProductDto;
import com.jagdev.e_commerceBackend.exception.ProductNotFoundException;
import com.jagdev.e_commerceBackend.model.Category;
import com.jagdev.e_commerceBackend.model.Image;
import com.jagdev.e_commerceBackend.model.Product;
import com.jagdev.e_commerceBackend.repository.CategoryRepository;
import com.jagdev.e_commerceBackend.repository.ImageRepository;
import com.jagdev.e_commerceBackend.repository.ProductRepository;
import com.jagdev.e_commerceBackend.request_dto.AddProductRequestDto;
import com.jagdev.e_commerceBackend.request_dto.ProductUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ImageRepository  imageRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequestDto addProductRequestDto) {
        //if the category is found in the database
        //if yes. set it as the new product category
        //if no,then save it as a new category
        //Then set as the new product category
        if(productExists(addProductRequestDto.getName(),addProductRequestDto.getBrand())){
            try {
                throw new AlreadyBoundException(addProductRequestDto.getBrand() +" "+addProductRequestDto.getName()+" already exists,you may update this product instead");
            } catch (AlreadyBoundException e) {
                throw new RuntimeException(e);
            }
        }
        Category category = Optional.ofNullable(categoryRepository.findByName(addProductRequestDto.getCategory()))
                .orElseGet(()->{
                    Category newCategory = new Category(addProductRequestDto.getCategory());
                    return categoryRepository.save(newCategory);
                });
        return productRepository.save(createProduct(addProductRequestDto,category));

    }

    private boolean productExists(String name,String brand){
        return productRepository.existsByNameAndBrand(name,brand);
    }


    private Product createProduct(AddProductRequestDto addProductRequestDto, Category category){
        Product product = new Product();
        product.setName(addProductRequestDto.getName());
        product.setBrand(addProductRequestDto.getBrand());
        product.setPrice(addProductRequestDto.getPrice());
        product.setInventory(addProductRequestDto.getInventory());
        product.setDescription(addProductRequestDto.getDescription());
        product.setCategory(category);
        return product;

    }
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                ()-> new ProductNotFoundException("product not found")
        );
    }


    @Override
    public Product updateProductById(ProductUpdateRequestDto productUpdateRequestDto, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, productUpdateRequestDto))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("product not found"));
    }

    private Product updateExistingProduct(Product existingproduct, ProductUpdateRequestDto  productUpdateRequestDto) {
        existingproduct.setName(productUpdateRequestDto.getName());
        existingproduct.setBrand(productUpdateRequestDto.getBrand());
        existingproduct.setPrice(productUpdateRequestDto.getPrice());
        existingproduct.setInventory(productUpdateRequestDto.getInventory());
        existingproduct.setDescription(productUpdateRequestDto.getDescription());

        // Fix: Use category's name from DTO, not product's name
        if (productUpdateRequestDto.getCategory() != null) {
            Category category = categoryRepository.findByName(productUpdateRequestDto.getCategory().getName());
            if (category != null) {
                existingproduct.setCategory(category);
            }
        }
        return existingproduct;



    }


    @Override
    public void deleteProductById( Long id) {
        productRepository.findById(id)
                .ifPresentOrElse((productRepository::delete),
        ()-> {throw new ProductNotFoundException("product not found");}
                );

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByNameIgnoreCase(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandIgnoreCaseAndNameIgnoreCase(brand, name);
    }

  @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();

    }

    @Override
   public ProductDto convertToDto(Product product) {
       ProductDto productDto = modelMapper.map(product, ProductDto.class);
       List<Image> images = imageRepository.findByProductId(product.getId());
       List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(
               image, ImageDto.class

       )).toList();
   productDto.setImages(imageDtos);
   return productDto;
   }

}
