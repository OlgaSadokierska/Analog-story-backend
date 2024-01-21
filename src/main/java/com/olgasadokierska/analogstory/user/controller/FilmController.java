package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.model.Film;
import com.olgasadokierska.analogstory.user.service.FilmService;
import com.olgasadokierska.analogstory.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/films")
@CrossOrigin(origins = "http://localhost:3000")
public class FilmController {
    private final FilmService filmService;
    private final UserService userService;

    @Autowired
    public FilmController(FilmService filmService, UserService userService) {
        this.filmService = filmService;
        this.userService = userService;
    }

    @GetMapping("/films/{userId}")
    public ResponseEntity<List<Film>> getUserFilms(@PathVariable long userId) {
        List<Film> films = userService.getUserFilms(userId);
        return ResponseEntity.ok(films);
    }
}
