package com.sourav.ecommerce.controller;

import com.sourav.ecommerce.model.Product;
import com.sourav.ecommerce.payload.ProductDTO;
import com.sourav.ecommerce.payload.ProductResponse;
import com.sourav.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product, @PathVariable Long categoryId) {
           ProductDTO productDTO = productService.addProduct(product,categoryId);
           return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts() {
        ProductResponse productResponse = productService.getAll();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/{categoryId}")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId) {
        ProductResponse productResponse = productService.getByCategory(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyWord(@PathVariable String keyword) {
        ProductResponse productResponse = productService.getByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
          ProductDTO updatedProduct =  productService.updateProduct(productId, product);
          return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    private void getUpdateProduct(Long productId, Product product) {
        productService.updateProduct(productId, product);
    }
}
