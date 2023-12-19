package com.olgasadokierska.analogstory.user.controller;
import com.olgasadokierska.analogstory.user.dtos.UserDto;
import com.olgasadokierska.analogstory.user.dtos.UserMediaDTO;
import com.olgasadokierska.analogstory.user.model.Reservation;
import com.olgasadokierska.analogstory.user.service.ReservationService;
import com.olgasadokierska.analogstory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://localhost:3000" )
public class UserController {

    private final UserService userService;
    private final ReservationService resrvationService;

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/by-email")
    public ResponseEntity<Long> getUserIdByEmail(@RequestParam("email") String email) {
        Long userId = userService.findUserIdByEmail(email);
        return ResponseEntity.ok(userId);
    }

    //wys. rezerwacji wszystykich
    @GetMapping("/allReservations")
    public ResponseEntity<List<Reservation>> getAllReservations(){
        List<Reservation> reservations = resrvationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

   @GetMapping("/{userId}/media")
    public ResponseEntity<UserMediaDTO> getUserMedia(@PathVariable long userId){
        UserMediaDTO userMediaDTO = userService.getUserMedia(userId);
        return ResponseEntity.ok(userMediaDTO);
   }
}
