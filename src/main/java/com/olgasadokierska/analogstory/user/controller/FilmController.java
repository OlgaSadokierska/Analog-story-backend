package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.exception.CustomException;
import com.olgasadokierska.analogstory.user.model.Film;
import com.olgasadokierska.analogstory.user.service.FilmService;
import com.olgasadokierska.analogstory.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    //dodawanie nowej kliszy

    @PostMapping("/add/{userId}")
    public ResponseEntity<Film> addFilm(@PathVariable long userId, @RequestBody Film film) {
        try {
            Film addedFilm = filmService.addFilm(userId, film);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedFilm);
        } catch (CustomException e) {
            throw new CustomException("Błąd podczas dodawania kliszy", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
// wystawiania kliszy na sprzedaż
/*@PostMapping("/sell/{userId}")
public ResponseEntity<FilmDTO> sellFilm(@PathVariable Long userId, @RequestBody FilmDTO filmDTO) {
    try {
        FilmDTO soldFilm = filmService.sellFilm(userId, filmDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(soldFilm);
    } catch (CustomException e) {
        throw new CustomException("Błąd podczas wystawiania filmu na sprzedaż", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
*/

}
