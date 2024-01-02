package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.dtos.SignUpDto;
import com.olgasadokierska.analogstory.user.dtos.UserDto;
import com.olgasadokierska.analogstory.user.dtos.UserMediaDTO;
import com.olgasadokierska.analogstory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://localhost:3000" )
public class UserController {

    private final UserService userService;

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
    public ResponseEntity<Integer[]> getUserIdByEmail(@RequestParam("email") String email) {
        Integer[] userId = userService.findUserDataByEmail(email);
        return ResponseEntity.ok(userId);
    }

   @GetMapping("/{userId}/media")
    public ResponseEntity<UserMediaDTO> getUserMedia(@PathVariable long userId){
        UserMediaDTO userMediaDTO = userService.getUserMedia(userId);
        return ResponseEntity.ok(userMediaDTO);
   }

    @PutMapping("/id/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long userId,
            @RequestBody SignUpDto signUpDto
    ) {
        UserDto updatedUser = userService.updateUser(userId, signUpDto);
        return ResponseEntity.ok(updatedUser);
    }
}
