package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.service.ProductService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.created(URI.create("/products/" + createdProduct.getId())).body(createdProduct);
    }
//edycja produktu
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductDto updatedProductDto) {
        ProductDto updatedProduct = productService.updateProduct(productId, updatedProductDto);
        return ResponseEntity.ok(updatedProduct);
    }
//uswanie wybranego produktu
   /* @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
    productService.deleteProduct(productId);
    return ResponseEntity.noContent().build();
    }
*/
}