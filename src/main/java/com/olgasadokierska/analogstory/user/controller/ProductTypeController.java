package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.model.ProductType;
import com.olgasadokierska.analogstory.user.service.ProductTypeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/product-types")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @GetMapping
    public ResponseEntity<List<ProductType>> getAllProductTypes() {
        List<ProductType> productTypes = productTypeService.getAllProductTypes();
        return ResponseEntity.ok(productTypes);
    }

    @PostMapping
    public ResponseEntity<ProductType> createProductType(@RequestBody ProductType productType) {
        ProductType createdProductType = productTypeService.createProductType(productType);
        return ResponseEntity.created(URI.create("/api/v1/product-types/" + createdProductType.getId()))
                .body(createdProductType);
    }
}
