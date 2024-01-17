package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.exception.AppException;
import com.olgasadokierska.analogstory.user.exception.CannotDeleteProductException;
import com.olgasadokierska.analogstory.user.exception.ProductNotFoundException;
import com.olgasadokierska.analogstory.user.model.Camera;
import com.olgasadokierska.analogstory.user.model.Film;
import com.olgasadokierska.analogstory.user.repository.CameraRepository;
import com.olgasadokierska.analogstory.user.repository.CartRepository;
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
    private final CartRepository cartRepository;

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
                    boolean isProductInCart = cartRepository.existsByProductId(product.getId());

                    return isCameraForSale && isFilmForSale && !isProductInCart;
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
    @Transactional
    public void deleteProduct(Long productId) throws ProductNotFoundException, CannotDeleteProductException {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // Sprawdzenie, czy Camera ma isForSale ustawione na true
            cameraRepository.findByProductId(productId).ifPresent(camera -> {
                if (!camera.getIsForSale()) {
                    throw new CannotDeleteProductException("Nie można usunąć produktu, ponieważ powiązana kamera nie jest na sprzedaż.");
                }
            });

            // Sprawdzenie, czy Film ma isForSale ustawione na true
            filmRepository.findByProductId(productId).ifPresent(film -> {
                if (!film.getIsForSale()) {
                    throw new CannotDeleteProductException("Nie można usunąć produktu, ponieważ powiązany film nie jest na sprzedaż.");
                }
            });

            // Usuwanie związanych rekordów z tabel Camera i Film
            cameraRepository.deleteByProductId(productId);
            filmRepository.deleteByProductId(productId);

            // Usuwanie produktu
            productRepository.delete(product);
        } else {
            throw new ProductNotFoundException("Produkt o ID " + productId + " nie został znaleziony");
        }
    }
}

