package com.sourav.ecommerce.service;

import com.sourav.ecommerce.payload.CategoryDTO;
import com.sourav.ecommerce.payload.CategoryResponse;

public interface CategoryService {
     CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
     CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
