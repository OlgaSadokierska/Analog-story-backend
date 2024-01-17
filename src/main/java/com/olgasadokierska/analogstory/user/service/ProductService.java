package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.exception.AppException;
import com.olgasadokierska.analogstory.user.exception.CannotDeleteProductException;
import com.olgasadokierska.analogstory.user.exception.ProductNotFoundException;
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
import java.util.Optional;
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
/*
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
    }
*/


    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .filter(product -> {
                    boolean isCameraForSale = !cameraRepository.existsByProductId(product.getId()) ||
                            cameraRepository.findByProductId(product.getId())
                                    .map(Camera::getIsForSale)
                                    .orElse(false);

                    boolean isFilmForSale = !filmRepository.existsByProductId(product.getId()) ||
                            filmRepository.findByProductId(product.getId())
                                    .map(Film::getIsForSale)
                                    .orElse(false);

                    // Sprawdzenie, czy produkt nie ma powiązań z Camera, gdzie isForSale jest ustawione na false
                    // oraz nie ma powiązań z Film, gdzie isForSale jest ustawione na false
                    return isCameraForSale && isFilmForSale;
                })
                .map(product -> {
                    ProductDto productDto = productMapper.toProductDto(product);

                    // Dodanie informacji o modelu i marce związanej z Camera
                    cameraRepository.findByProductId(product.getId()).ifPresent(camera -> {
                        productDto.setModel(camera.getModel());
                        productDto.setBrand(camera.getBrand());
                    });

                    return productDto;
                })
                .collect(Collectors.toList());
    }










    public String getProductInfoById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException("Produkt o ID " + productId + " nie istnieje", HttpStatus.NOT_FOUND));


        return "Opis: " + product.getDescription() + ", Cena: " + product.getPrice();
    }
    @Transactional(readOnly = true)
    public ProductDto getProductDtoById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException("Product with ID " + productId + " not found", HttpStatus.NOT_FOUND));


        return productMapper.toProductDto(product);

    }
//edycja produktu
    @Transactional
    public ProductDto updateProduct(Long productId, ProductDto updatedProductDto) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new AppException("Product with ID " + productId + " not found", HttpStatus.NOT_FOUND));

        existingProduct.setDescription(updatedProductDto.getDescription());
        existingProduct.setPrice(updatedProductDto.getPrice());

        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toProductDto(updatedProduct);
    }
    // usuwanie wybrango produktu
    /*@Transactional
    public void deleteProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // Sprawdzenie czy istnieją powiązania z Camera lub Film
            boolean hasAssociations = cameraRepository.existsByProductId(productId)
                    || filmRepository.existsByProductId(productId);

            // Sprawdzenie czy pole isForSale jest ustawione na true
            boolean isForSale = false;

            // Sprawdzenie warunków usuwania
            if (!hasAssociations && !isForSale) {
                productRepository.delete(product);
            } else {
                throw new CannotDeleteProductException("Cannot delete product with associations or isForSale set to true.");
            }
        } else {
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }
    }*/
}

