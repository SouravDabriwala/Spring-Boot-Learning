package com.sourav.ecommerce.repositories;

import com.sourav.ecommerce.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByCategoryName(@NotBlank(message = "Category name must not be blank") @Size(min = 3, max = 20, message = "Category name must be between 3 and 20 characters") String categoryName);
}
