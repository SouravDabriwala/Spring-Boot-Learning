package com.sourav.ecommerce.service;

import com.sourav.ecommerce.payload.ProductDTO;
import com.sourav.ecommerce.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);

    ProductResponse getAll();

    ProductResponse getByCategory(Long categoryId);

    ProductResponse getByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);
}
