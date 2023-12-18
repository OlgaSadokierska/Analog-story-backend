package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.dtos.UserMediaDTO;
import com.olgasadokierska.analogstory.user.model.Reservation;
import com.olgasadokierska.analogstory.user.service.ReservationService;
import com.olgasadokierska.analogstory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
            return ResponseEntity.ok(userMediaDTO);
        }
    }

}
