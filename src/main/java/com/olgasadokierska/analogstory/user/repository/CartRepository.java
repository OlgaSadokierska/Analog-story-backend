package com.olgasadokierska.analogstory.user.repository;
import com.olgasadokierska.analogstory.user.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface CartRepository extends JpaRepository<Cart, Long> {

}
