package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.dtos.CartDTO;
import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.dtos.UserDto;
import com.olgasadokierska.analogstory.user.exception.AppException;
import com.olgasadokierska.analogstory.user.mapper.CartMapper;
import com.olgasadokierska.analogstory.user.model.Cart;
import com.olgasadokierska.analogstory.user.model.Product;
import com.olgasadokierska.analogstory.user.model.Reservation;
import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.repository.CartRepository;
import com.olgasadokierska.analogstory.user.repository.ProductRepository;
import com.olgasadokierska.analogstory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductService productService;
    private final UserService userService;
    private final ReservationService reservationService;
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

    private CartDTO mapCartToCartDTOWithProductInfo(Cart cart) {
        CartDTO cartDTO = cartMapper.cartToCartDTO(cart);
        if (cart.getProduct() != null) {
            ProductDto productDto = productService.getProductDtoById(cart.getProduct().getId());
            cartDTO.setProductDto(productDto);
        }
        if (cart.getProduct() != null) {
            UserDto userDto = userService.findById(cart.getUser().getId());
            cartDTO.setUserDto(userDto);
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

    @Transactional
    public void addToCart(CartDTO cartDTO) {
        Cart cart = cartMapper.cartDTOToCart(cartDTO);

        User user = userRepository.findById(cartDTO.getUserId())
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        Product product = productRepository.findById(cartDTO.getProductId())
                .orElseThrow(() -> new AppException("Product not found", HttpStatus.NOT_FOUND));
        cart.setUser(user);
        cart.setProduct(product);
        cartRepository.save(cart);
    }

    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        return user.getId();
    }

    @Transactional
    public void addToCart(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException("Product not found", HttpStatus.NOT_FOUND));
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cartRepository.save(cart);
    }

    @Transactional
    public void markCartAsPurchased(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException("Koszyk nie znaleziony", HttpStatus.NOT_FOUND));
        if (cart.getIsPurchased()) {
            throw new AppException("Koszyk został już zaakceptowany", HttpStatus.BAD_REQUEST);
        }
        cart.setIsPurchased(true);
        cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    public List<CartDTO> getAcceptedCarts() {
        return cartRepository.findByIsPurchasedTrue().stream()
                .map(this::mapCartToCartDTOWithProductInfo)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CartDTO> getAcceptedCartsForUser(Long userId) {
        return cartRepository.findByUserIdAndIsPurchasedTrue(userId).stream()
                .map(this::mapCartToCartDTOWithProductInfo)
                .collect(Collectors.toList());
    }

    @Transactional
    public void moveCartToReservation(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException("Koszyk nie znaleziony", HttpStatus.NOT_FOUND));
        Reservation reservation = Reservation.builder()
                .user(cart.getUser())
                .product(cart.getProduct())
                .reservationDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(3))
                .build();
        reservationService.saveReservation(reservation);
    }

    @Transactional
    public void deleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException("Koszyk nie znaleziony", HttpStatus.NOT_FOUND));
        cartRepository.delete(cart);
    }
}
