package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.exception.CannotDeleteProductException;
import com.olgasadokierska.analogstory.user.exception.ProductNotFoundException;
import com.olgasadokierska.analogstory.user.mapper.ProductMapper;
import com.olgasadokierska.analogstory.user.model.Product;
import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product createdProduct = productMapper.toProduct(productDto);
        return ResponseEntity.created(URI.create("/products/" + createdProduct.getId())).body(createdProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductDto updatedProductDto) {
        ProductDto updatedProduct = productService.updateProduct(productId, updatedProductDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.noContent().build();
        } catch (ProductNotFoundException | CannotDeleteProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
