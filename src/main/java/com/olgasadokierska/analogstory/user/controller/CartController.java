package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.dtos.CartDTO;
import com.olgasadokierska.analogstory.user.dtos.UserDto;
import com.olgasadokierska.analogstory.user.exception.AppException;
import com.olgasadokierska.analogstory.user.model.Cart;
import com.olgasadokierska.analogstory.user.model.Product;
import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.repository.CartRepository;
import com.olgasadokierska.analogstory.user.service.CartService;
import com.olgasadokierska.analogstory.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.olgasadokierska.analogstory.user.service.ProductService;
import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import org.springframework.security.core.Authentication;

import java.net.URI;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/carts")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;



    @GetMapping("")
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<CartDTO> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/unaccepted")
    public ResponseEntity<List<CartDTO>> getUnacceptedCarts() {
        List<CartDTO> unacceptedCarts = cartService.getUnacceptedCarts();
        return ResponseEntity.ok(unacceptedCarts);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<CartDTO>> getUserCart(@PathVariable("user_id") Long userId) {
        // Pobierz koszyk użytkownika
        List<CartDTO> userCart = cartService.getUserCart(userId);

        return ResponseEntity.ok(userCart);
    }

    @PostMapping("/add-to-cart/{userId}/{productId}")
    public ResponseEntity<String> addToCart(
            @PathVariable Long userId,
            @PathVariable Long productId) {

        cartService.addToCart(userId, productId);

        return ResponseEntity.ok("Product added to the cart successfully");
    }
    // akceptowanie koszyka
    @PostMapping("/mark-as-purchased/{cartId}")
    public ResponseEntity<String> markAsPurchased(@PathVariable Long cartId) {
        cartService.markCartAsPurchased(cartId);

        return ResponseEntity.ok("Cart marked as purchased successfully");
    }
    //wyswietlanie wszytskich koszyków zakceptowanych
    @GetMapping("/accepted")
    public ResponseEntity<List<CartDTO>> getAcceptedCarts() {
        List<CartDTO> acceptedCarts = cartService.getAcceptedCarts();
        return ResponseEntity.ok(acceptedCarts);
    }
    //wyswietlanie koszyków zakceptowanych dla uzytkownika zalogowanego
    @GetMapping("/accepted/{userId}")
    public ResponseEntity<List<CartDTO>> getAcceptedCartsForUser(@PathVariable Long userId) {
        List<CartDTO> acceptedCarts = cartService.getAcceptedCartsForUser(userId);
        return ResponseEntity.ok(acceptedCarts);
    }

}









