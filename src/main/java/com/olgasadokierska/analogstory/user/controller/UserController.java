package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.dtos.UserDto;
import com.olgasadokierska.analogstory.user.dtos.UserMediaDTO;
import com.olgasadokierska.analogstory.user.model.Reservation;
import com.olgasadokierska.analogstory.user.service.ReservationService;
import com.olgasadokierska.analogstory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final ReservationService reservationService;
    @GetMapping("")
    public ResponseEntity<List<String>> getAllUsers() {
        return ResponseEntity.ok(Arrays.asList("cos","cos2"));
    }
    //wyłuskanie id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
    //usuwanie uzytkownika
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
    //wyswietlanie klisz i aparatow
    @GetMapping("/{userId}/media")
    public ResponseEntity<UserMediaDTO> getUserMedia(@PathVariable long userId) {
        UserMediaDTO userMediaDTO = userService.getUserMedia(userId);
        return ResponseEntity.ok(userMediaDTO);
    }
    //wys. rezerwacji dla wszytskich

    @GetMapping("/allReservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }



}
