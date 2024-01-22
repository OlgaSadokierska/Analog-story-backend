package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.model.Film;
import com.olgasadokierska.analogstory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/films")
@CrossOrigin(origins = "http://localhost:3000")
public class FilmController {

    private final UserService userService;

    @GetMapping("/films/{userId}")
    public ResponseEntity<List<Film>> getUserFilms(@PathVariable long userId) {
        List<Film> films = userService.getUserFilms(userId);
        return ResponseEntity.ok(films);
    }
}
