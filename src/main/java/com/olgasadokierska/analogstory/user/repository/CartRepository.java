package com.olgasadokierska.analogstory.user.repository;
import com.olgasadokierska.analogstory.user.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByIsPurchasedFalse();

}
