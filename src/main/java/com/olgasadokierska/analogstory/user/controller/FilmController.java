package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.dtos.CameraDTO;
import com.olgasadokierska.analogstory.user.dtos.FilmDTO;
import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.exception.CustomException;
import com.olgasadokierska.analogstory.user.model.Film;
import com.olgasadokierska.analogstory.user.service.FilmService;
import com.olgasadokierska.analogstory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/films")
@CrossOrigin(origins = "http://localhost:3000")
public class FilmController {

    private final UserService userService;
    private final FilmService filmService;

    @GetMapping("/films/{userId}")
    public ResponseEntity<List<Film>> getUserFilms(@PathVariable long userId) {
        List<Film> films = userService.getUserFilms(userId);
        return ResponseEntity.ok(films);
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<Film> addFilm(@PathVariable long userId, @RequestBody FilmDTO filmDTO,
                                        @RequestBody(required = false) CameraDTO cameraDTO) {
        try {
            Film addedFilm = filmService.addFilm(userId, filmDTO, cameraDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedFilm);
        } catch (CustomException e) {
            throw new CustomException("Błąd podczas dodawania kliszy", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/setForSale/{filmId}")
    public ResponseEntity<FilmDTO> setFilmForSale(@PathVariable long filmId, @RequestBody ProductDto productDto) {
        try {
            FilmDTO filmForSale = filmService.setFilmForSale(filmId, productDto);
            return ResponseEntity.ok(filmForSale);
        } catch (CustomException e) {
            throw new CustomException("Błąd podczas ustawiania filmu na sprzedaż", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
