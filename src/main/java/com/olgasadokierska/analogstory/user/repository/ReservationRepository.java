package com.olgasadokierska.analogstory.user.repository;

import com.olgasadokierska.analogstory.user.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}