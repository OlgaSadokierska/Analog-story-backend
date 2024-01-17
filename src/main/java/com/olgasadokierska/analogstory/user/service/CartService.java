package com.olgasadokierska.analogstory.user.service;


import com.olgasadokierska.analogstory.user.dtos.CartDTO;
import com.olgasadokierska.analogstory.user.exception.AppException;
import com.olgasadokierska.analogstory.user.mapper.CartMapper;
import com.olgasadokierska.analogstory.user.model.Cart;
import com.olgasadokierska.analogstory.user.repository.CartRepository;
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

    @Transactional
    public CartDTO createCart(CartDTO cartDTO) {
        Cart cart = cartMapper.cartDTOToCart(cartDTO);
        Cart createdCart = cartRepository.save(cart);
        return cartMapper.cartToCartDTO(createdCart);
    }

    private CartDTO mapCartToCartDTOWithProductInfo(Cart cart) {
        CartDTO cartDTO = cartMapper.cartToCartDTO(cart);


        if (cart.getProduct() != null) {


        }

        return cartDTO;
    }


}
