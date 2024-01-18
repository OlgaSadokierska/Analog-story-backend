package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.dtos.ReservationDTO;
import com.olgasadokierska.analogstory.user.exception.AppException;
import com.olgasadokierska.analogstory.user.model.Reservation;
import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.repository.ReservationRepository;
import com.olgasadokierska.analogstory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
// wys. rezerwacji po terminie (wyznacznikim terminu jest aktualna data)
    public List<Reservation> getExpiredReservations() {
        LocalDateTime currentDate = LocalDateTime.now();

        return reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getExpirationDate().isBefore(currentDate))
                .collect(Collectors.toList());
    }
    // wys. rezerwacji które są w terminie
    public List<Reservation> getActiveReservations() {
        LocalDateTime currentDate = LocalDateTime.now();

        return reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getExpirationDate().isAfter(currentDate))
                .collect(Collectors.toList());
    }
    //usuwanie rezerwacji
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new AppException("Rezerwacja nie znaleziona", HttpStatus.NOT_FOUND));

        reservationRepository.delete(reservation);
    }
    //edytowanie rezerwacji
    public void updateReservationDate(Long reservationId, ReservationDTO reservationDTO) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new AppException("Rezerwacja nie znaleziona", HttpStatus.NOT_FOUND));

        // Aktualizacja tylko daty rezerwacji
        existingReservation.setReservationDate(reservationDTO.getReservationDate());
        existingReservation.setExpirationDate(reservationDTO.getExpirationDate());

        reservationRepository.save(existingReservation);
    }
}