package com.jagdev.e_commerceBackend.service.category;

import com.jagdev.e_commerceBackend.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category,Long id);
    void deleteCategory(Long categoryId);

}
