package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.model.ProductType;
import com.olgasadokierska.analogstory.user.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public List<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll();
    }

    public ProductType createProductType(ProductType productType) {
        return productTypeRepository.save(productType);
    }
}

