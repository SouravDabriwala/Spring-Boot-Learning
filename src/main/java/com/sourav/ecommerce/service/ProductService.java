package com.sourav.ecommerce.service;

import com.sourav.ecommerce.model.Product;
import com.sourav.ecommerce.payload.ProductDTO;
import com.sourav.ecommerce.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Product product, Long categoryId);

    ProductResponse getAll();

    ProductResponse getByCategory(Long categoryId);

    ProductResponse getByKeyword(String keyword);
}
