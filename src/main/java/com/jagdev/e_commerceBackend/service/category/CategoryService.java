package com.jagdev.e_commerceBackend.service.category;

import com.jagdev.e_commerceBackend.exception.AlreadyExistsEXception;
import com.jagdev.e_commerceBackend.exception.ResourceNotFoundException;
import com.jagdev.e_commerceBackend.model.Category;
import com.jagdev.e_commerceBackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("category not found..!")
        );
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        String categoryName = Optional.ofNullable(category)
            .map(Category::getName)
            .map(String::trim)
            .filter(name -> !name.isEmpty())
            .orElseThrow(() -> new IllegalArgumentException("category name must not be empty"));

        if (categoryRepository.existsByName(categoryName)) {
            throw new AlreadyExistsEXception(categoryName + " already exist");
        }

        // Persist a brand-new entity so request payload id/products never trigger merge.
        return categoryRepository.save(new Category(categoryName));
    }

    @Override
    public Category updateCategory(Category category,Long id){
        return Optional.ofNullable(getCategoryById(id))
                .map(oldcategory ->{
                    oldcategory.setName(category.getName());
                    return categoryRepository.save(oldcategory);
                }).orElseThrow(
                        ()->new ResourceNotFoundException("category not found..!")
                );
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.findById(categoryId).ifPresentOrElse(categoryRepository::delete,
                ()->{throw new ResourceNotFoundException("category not found..!");});
    }
}
