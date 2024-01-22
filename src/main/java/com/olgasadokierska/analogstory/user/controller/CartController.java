package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.dtos.CartDTO;
import com.olgasadokierska.analogstory.user.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/carts")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private final CartService cartService;

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

    @PostMapping("/mark-as-purchased/{cartId}")
    public ResponseEntity<String> markAsPurchased(@PathVariable Long cartId) {
        cartService.markCartAsPurchased(cartId);
        cartService.moveCartToReservation(cartId);
        return ResponseEntity.ok("Cart marked as purchased and moved to reservation successfully");
    }

    @GetMapping("/accepted")
    public ResponseEntity<List<CartDTO>> getAcceptedCarts() {
        List<CartDTO> acceptedCarts = cartService.getAcceptedCarts();
        return ResponseEntity.ok(acceptedCarts);
    }

    @GetMapping("/accepted/{userId}")
    public ResponseEntity<List<CartDTO>> getAcceptedCartsForUser(@PathVariable Long userId) {
        List<CartDTO> acceptedCarts = cartService.getAcceptedCartsForUser(userId);
        return ResponseEntity.ok(acceptedCarts);
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok("Cart deleted successfully");
    }
}
