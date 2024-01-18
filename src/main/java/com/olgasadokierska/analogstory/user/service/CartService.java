package com.olgasadokierska.analogstory.user.service;


import com.olgasadokierska.analogstory.user.dtos.CartDTO;
import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.exception.AppException;
import com.olgasadokierska.analogstory.user.mapper.CartMapper;
import com.olgasadokierska.analogstory.user.model.Cart;
import com.olgasadokierska.analogstory.user.model.Product;
import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.repository.CartRepository;
import com.olgasadokierska.analogstory.user.repository.ProductRepository;
import com.olgasadokierska.analogstory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.olgasadokierska.analogstory.user.dtos.CartDTO;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductService productService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;




    @Transactional(readOnly = true)
    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll().stream()
                .map(cartMapper::cartToCartDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<CartDTO> getAllCartsWithProductInfo() {
        return cartRepository.findAll().stream()
                .map(this::mapCartToCartDTOWithProductInfo)
                .collect(Collectors.toList());
    }

    /*@Transactional
    public CartDTO createCart(CartDTO cartDTO) {
        // Sprawdzamy, czy produkt o podanym ID istnieje
        ProductDto productDto = productService.getProductDtoById(cartDTO.getProductId());
        if (productDto == null) {
            throw new AppException("Product with ID " + cartDTO.getProductId() + " not found", HttpStatus.NOT_FOUND);
        }

        Cart cart = cartMapper.cartDTOToCart(cartDTO);
        Cart createdCart = cartRepository.save(cart);
        return cartMapper.cartToCartDTO(createdCart);
    }*/





    /*@Transactional
    public CartDTO createCart(CartDTO cartDTO) {
        Cart cart = cartMapper.cartDTOToCart(cartDTO);
        Cart createdCart = cartRepository.save(cart);
        return cartMapper.cartToCartDTO(createdCart);
    }*/





    private CartDTO mapCartToCartDTOWithProductInfo(Cart cart) {
        CartDTO cartDTO = cartMapper.cartToCartDTO(cart);

        if (cart.getProduct() != null) {
            ProductDto productDto = productService.getProductDtoById(cart.getProduct().getId());
            cartDTO.setProductDto(productDto);
        }

        return cartDTO;
    }

    @Transactional(readOnly = true)
    public List<CartDTO> getUnacceptedCarts() {
        return cartRepository.findByIsPurchasedFalse().stream()
                .map(this::mapCartToCartDTOWithProductInfo)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CartDTO> getUserCart(Long userId) {
        return cartRepository.findByUserId(userId).stream()
                .map(this::mapCartToCartDTOWithProductInfo)
                .collect(Collectors.toList());
    }

}
