package com.sourav.ecommerce.service;

import com.sourav.ecommerce.exception.ResourceNotFoundException;
import com.sourav.ecommerce.exception.isEmptyException;
import com.sourav.ecommerce.model.Category;
import com.sourav.ecommerce.model.Product;
import com.sourav.ecommerce.payload.ProductDTO;
import com.sourav.ecommerce.payload.ProductResponse;
import com.sourav.ecommerce.repositories.CategoryRepository;
import com.sourav.ecommerce.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        product.setCategory(category);
        product.setImage("default.png");
        double specialPrice = product.getPrice() - ((product.getDiscount()*.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAll() {
        List<Product> products = productRepository.findAll();

        return getProductResponse(products);

    }

    @Override
    public ProductResponse getByCategory(Long categoryId) {
         Category category = categoryRepository.findById(categoryId)
                 .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Product> products = productRepository.findByCategory_CategoryIdOrderByPriceAsc(categoryId);
        return getProductResponse(products);
    }

    @Override
    public ProductResponse getByKeyword(String keyword) {
         List<Product> products = productRepository.findByProductNameContainingIgnoreCase(keyword);
         return getProductResponse(products);
    }

    @Override
    public ProductDTO updateProduct(Long productId, Product product) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        product.setProductId(existingProduct.getProductId());
        product.setImage(existingProduct.getImage());
        product.setSpecialPrice(product.getPrice() - ((product.getDiscount()*.01) * product.getPrice()));
        Product savedProduct =  productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
           Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
           productRepository.delete(product);
           return modelMapper.map(product, ProductDTO.class);
    }

    private ProductResponse getProductResponse(List<Product> products) {
        if(products.isEmpty()){
            throw new isEmptyException("There are no products to show!");
        }

        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);

        return productResponse;
    }
}
