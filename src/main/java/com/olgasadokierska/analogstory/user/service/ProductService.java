package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.exception.AppException;
import com.olgasadokierska.analogstory.user.exception.CannotDeleteProductException;
import com.olgasadokierska.analogstory.user.exception.CustomException;
import com.olgasadokierska.analogstory.user.exception.ProductNotFoundException;
import com.olgasadokierska.analogstory.user.model.Camera;
import com.olgasadokierska.analogstory.user.model.Film;
import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.repository.*;
import com.olgasadokierska.analogstory.user.mapper.ProductMapper;
import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final UserRepository userRepository;

    @Transactional
    public ProductDto createProduct(Long userId,ProductDto productDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("Użytkownik o ID " + userId + " nie istnieje", HttpStatus.NOT_FOUND));
        Product product = productMapper.toProduct(productDto);

        product.setModel(productDto.getModel());
        product.setBrand(productDto.getBrand());
        product.setUser(user);

        Product savedProduct = productRepository.save(product);
        return productMapper.toProductDto(savedProduct);
    }


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
                    boolean isProductInCart = cartRepository.existsByProductId(product.getId());
                    return isCameraForSale && isFilmForSale && !isProductInCart;
                })
                .map(product -> {
                    ProductDto productDto = productMapper.toProductDto(product);
                    productDto.setUserId(product.getUser().getId());
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

    @Transactional
    public ProductDto updateProduct(Long productId, ProductDto updatedProductDto) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new AppException("Product with ID " + productId + " not found", HttpStatus.NOT_FOUND));
        existingProduct.setDescription(updatedProductDto.getDescription());
        existingProduct.setPrice(updatedProductDto.getPrice());
        existingProduct.setModel(updatedProductDto.getModel());
       existingProduct.setBrand(updatedProductDto.getBrand());
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toProductDto(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long productId) throws ProductNotFoundException, CannotDeleteProductException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            cameraRepository.findByProductId(productId).ifPresent(camera -> {
                if (!camera.getIsForSale()) {
                    throw new CannotDeleteProductException("Nie można usunąć produktu, ponieważ powiązana kamera nie jest na sprzedaż.");
                } else {
                    cameraRepository.delete(camera);
                }
            });

            filmRepository.findByProductId(productId).ifPresent(film -> {
                if (!film.getIsForSale()) {
                    throw new CannotDeleteProductException("Nie można usunąć produktu, ponieważ powiązany film nie jest na sprzedaż.");
                } else {
                    filmRepository.delete(film);
                }
            });
            productRepository.delete(product);
        } else {
            throw new ProductNotFoundException("Produkt o ID " + productId + " nie został znaleziony");
        }
    }


}
