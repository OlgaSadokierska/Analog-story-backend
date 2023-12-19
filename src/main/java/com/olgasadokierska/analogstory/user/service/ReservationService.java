package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.exception.AppException;
import com.olgasadokierska.analogstory.user.model.Reservation;
import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.repository.ReservationRepository;
import com.olgasadokierska.analogstory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("Użytkownik nie znaleziony", HttpStatus.NOT_FOUND));
        return reservationRepository.findByUser(user);
    }

}