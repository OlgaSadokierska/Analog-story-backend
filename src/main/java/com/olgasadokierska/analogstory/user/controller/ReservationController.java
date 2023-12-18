package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.dtos.UserMediaDTO;
import com.olgasadokierska.analogstory.user.model.Reservation;
import com.olgasadokierska.analogstory.user.service.ReservationService;
import com.olgasadokierska.analogstory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reservations")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;

    // Wyswietlanie rezerwacji dla zalogowanego użytkownika
    @GetMapping("/{userId}/media")
    public ResponseEntity<UserMediaDTO> getUserReservationsMedia(@PathVariable long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUser(userId);

        if (reservations.isEmpty()) {
            return ResponseEntity.status(404).body(new UserMediaDTO()); // Pusty wynik dla braku rezerwacji
        } else {

            UserMediaDTO userMediaDTO = new UserMediaDTO();
            userMediaDTO.setReservations(reservations);

            // Sprawdź, czy któraś rezerwacja ma expirationDate za 3 dni
            for (Reservation reservation : reservations) {
                LocalDateTime expirationDate = reservation.getExpirationDate();
                LocalDateTime threeDaysBefore = LocalDateTime.now().plusDays(3);

                if (expirationDate.truncatedTo(ChronoUnit.DAYS).isEqual(threeDaysBefore)) {

                    System.out.println("Uwaga! Data ważności rezerwacji za 3 dni: " + expirationDate);
                }

                // Sprawdź, czy data ważności rezerwacji minęła
                if (expirationDate.truncatedTo(ChronoUnit.DAYS).isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))) {

                    System.out.println("Uwaga! Rezerwacja wygasła: " + expirationDate);
                }
            }

            return ResponseEntity.ok(userMediaDTO);

        }
    }

}
