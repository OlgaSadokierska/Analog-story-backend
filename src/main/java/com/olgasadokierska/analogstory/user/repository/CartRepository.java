package com.olgasadokierska.analogstory.user.repository;

import com.olgasadokierska.analogstory.user.model.Cart;
import com.olgasadokierska.analogstory.user.model.Product;
import com.olgasadokierska.analogstory.user.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByIsPurchasedFalse();

    boolean existsByProductId(Long productId);

    List<Cart> findByIsPurchasedTrue();

    List<Cart> findByUserId(Long userId);

    List<Cart> findByUserIdAndIsPurchasedTrue(Long userId);

    List<Cart> findByUserIdAndIsPurchasedFalse(Long userId);
    boolean existsByProduct(Product product);
    List<Cart> findByProductId(Long productId);
}
