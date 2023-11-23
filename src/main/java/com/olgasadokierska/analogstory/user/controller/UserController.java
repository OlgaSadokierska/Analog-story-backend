package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.config.UserAuthenticationProvider;
import com.olgasadokierska.analogstory.user.dtos.CredentialsDto;
import com.olgasadokierska.analogstory.user.dtos.SignUpDto;
import com.olgasadokierska.analogstory.user.dtos.UserDto;
import com.olgasadokierska.analogstory.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @GetMapping("")
    public ResponseEntity<List<String>> getAllUsers() {
        return ResponseEntity.ok(Arrays.asList("cos","cos2"));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getLogin()));

        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.register(user);
        System.out.println("dupa" + user);

        createdUser.setToken(userAuthenticationProvider.createToken(user.getLogin()));

        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }


}
