package com.sourav.ecommerce.service;

import com.sourav.ecommerce.exception.APIException;
import com.sourav.ecommerce.exception.ResourceNotFoundException;
import com.sourav.ecommerce.model.Category;
import com.sourav.ecommerce.payload.CategoryDTO;
import com.sourav.ecommerce.payload.CategoryResponse;
import com.sourav.ecommerce.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder= sortOrder.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categoryList = categoryPage.getContent();
        if(categoryList.isEmpty()) throw new APIException("No categories found");

        List<CategoryDTO> categoryDTOS = categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setLastPage(categoryPage.isLast());
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());

        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
         Category savedCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
         if(savedCategory != null) throw new APIException("Category " + categoryDTO.getCategoryName() +  " already exists");

        Category category = modelMapper.map(categoryDTO, Category.class);

        Category createdCategory = categoryRepository.save(category);
        return modelMapper.map(createdCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
           categoryRepository.deleteById(categoryId);
           return  modelMapper.map(category, CategoryDTO.class);
        }
        else{
            throw new ResourceNotFoundException("Category", "categoryId", categoryId);
        }

    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if(optionalCategory.isPresent()){
            Category savedCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
            if(savedCategory != null) throw new APIException("Category " + categoryDTO.getCategoryName() +  " already exists");

            categoryDTO.setCategoryId(categoryId);
            Category category = modelMapper.map(categoryDTO, Category.class);
            Category saved = categoryRepository.save(category);
            return modelMapper.map(saved, CategoryDTO.class);
        }
        else{
            throw new ResourceNotFoundException("Category", "categoryId", categoryId);
        }

    }
}