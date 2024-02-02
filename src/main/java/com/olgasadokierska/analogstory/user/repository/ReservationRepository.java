package com.olgasadokierska.analogstory.user.repository;

import com.olgasadokierska.analogstory.user.model.Product;
import com.olgasadokierska.analogstory.user.model.Reservation;
import com.olgasadokierska.analogstory.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(User user);
    List<Reservation> findByProductId(Long productId);
    boolean existsByProductId(Long productId);
}
