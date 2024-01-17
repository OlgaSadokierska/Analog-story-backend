package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.exception.AppException;
import com.olgasadokierska.analogstory.user.model.Camera;
import com.olgasadokierska.analogstory.user.model.Film;
import com.olgasadokierska.analogstory.user.repository.CameraRepository;
import com.olgasadokierska.analogstory.user.repository.FilmRepository;
import com.olgasadokierska.analogstory.user.repository.ProductRepository;
import com.olgasadokierska.analogstory.user.mapper.ProductMapper;
import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CameraRepository cameraRepository;
    private final FilmRepository filmRepository;

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductDto(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
    }

    //

    public String getProductInfoById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException("Produkt o ID " + productId + " nie istnieje", HttpStatus.NOT_FOUND));


        return "Opis: " + product.getDescription() + ", Cena: " + product.getPrice();
    }
}
