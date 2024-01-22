package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.dtos.UserMediaDTO;
import com.olgasadokierska.analogstory.user.model.Reservation;
import com.olgasadokierska.analogstory.user.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import com.olgasadokierska.analogstory.user.dtos.ReservationDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reservations")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/{userId}/media")
    public ResponseEntity<UserMediaDTO> getUserReservationsMedia(@PathVariable long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUser(userId);

        if (reservations.isEmpty()) {
            return ResponseEntity.status(404).body(new UserMediaDTO());
        } else {
            UserMediaDTO userMediaDTO = new UserMediaDTO();
            userMediaDTO.setReservations(reservations);
            for (Reservation reservation : reservations) {
                LocalDateTime expirationDate = reservation.getExpirationDate();
                LocalDateTime threeDaysBefore = LocalDateTime.now().plusDays(3);
                if (expirationDate.truncatedTo(ChronoUnit.DAYS).isEqual(threeDaysBefore)) {
                    System.out.println("Uwaga! Data ważności rezerwacji za 3 dni: " + expirationDate);
                }
                if (expirationDate.truncatedTo(ChronoUnit.DAYS).isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))) {
                    System.out.println("Uwaga! Rezerwacja wygasła: " + expirationDate);
                }
            }
            return ResponseEntity.ok(userMediaDTO);
        }
    }

    @GetMapping("/{userId}/reservations")
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUser(userId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/expired")
    public ResponseEntity<List<Reservation>> getExpiredReservations() {
        List<Reservation> expiredReservations = reservationService.getExpiredReservations();
        return ResponseEntity.ok(expiredReservations);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Reservation>> getActiveReservations() {
        List<Reservation> activeReservations = reservationService.getActiveReservations();
        return ResponseEntity.ok(activeReservations);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);

        return ResponseEntity.ok("Rezerwacja została usunięta");
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<String> updateReservationDate(@PathVariable Long reservationId, @RequestBody ReservationDTO reservationDTO) {
        reservationService.updateReservationDate(reservationId, reservationDTO);
        return ResponseEntity.ok("Reservation date updated successfully");
    }
}
