package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.dtos.CameraDTO;
import com.olgasadokierska.analogstory.user.dtos.FilmDTO;
import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.exception.CustomException;
import com.olgasadokierska.analogstory.user.mapper.FilmMapperImpl;
import com.olgasadokierska.analogstory.user.model.Film;
import com.olgasadokierska.analogstory.user.repository.UserRepository;
import com.olgasadokierska.analogstory.user.service.CameraService;
import com.olgasadokierska.analogstory.user.service.FilmService;
import com.olgasadokierska.analogstory.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final CameraService cameraService;
    private final FilmMapperImpl filmMapper;

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

    //przspiswaynie aparatu do kliszy
    @PutMapping("/assignCamera/{filmId}/{cameraId}")
    public ResponseEntity<FilmDTO> assignCameraToFilm(@PathVariable long filmId, @PathVariable(required = false) Long cameraId) {
        try {
            FilmDTO assignedFilm = filmService.assignCameraToFilm(filmId, cameraId);
            return ResponseEntity.ok(assignedFilm);
        } catch (CustomException e) {
            throw new CustomException("Błąd podczas przypisywania kamery do filmu", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // wystawianie na sprzedaż kliszy
    @PutMapping("/setForSale/{filmId}")
    public ResponseEntity<FilmDTO> setFilmForSale(@PathVariable long filmId, @RequestBody ProductDto productDto) {
        try {
            FilmDTO filmForSale = filmService.setFilmForSale(filmId, productDto);
            return ResponseEntity.ok(filmForSale);
        } catch (CustomException e) {
            throw new CustomException("Błąd podczas ustawiania filmu na sprzedaż", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // uswanie przypisanego aparatu
    @DeleteMapping("/removeCamera/{filmId}")
    public ResponseEntity<FilmDTO> removeCameraFromFilm(@PathVariable long filmId) {
        try {
            FilmDTO removedFilm = filmService.removeCameraFromFilm(filmId);
            return ResponseEntity.ok(removedFilm);
        } catch (CustomException e) {
            throw new CustomException("Błąd podczas usuwania przypisanej kamery do filmu", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
// usuwanie kliszy
@DeleteMapping("/deleteFilm/{filmId}")
public ResponseEntity<String> deleteFilmAndProduct(@PathVariable long filmId) {
    try {
        filmService.deleteFilmAndProduct(filmId);
        return ResponseEntity.ok("Film został pomyślnie usunięty.");
    } catch (CustomException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystąpił błąd podczas usuwania filmu.");
    }
}
//update kliszy
@PutMapping("/updateDetails/{filmId}")
public ResponseEntity<FilmDTO> updateFilmDetails(@PathVariable Long filmId, @RequestBody FilmDTO updatedFilmDTO) {
    try {
        FilmDTO updatedFilm = filmService.updateFilm(filmId, updatedFilmDTO);
        return ResponseEntity.ok(updatedFilm);
    } catch (CustomException e) {
        return ResponseEntity.status(e.getStatus()).body(new FilmDTO());
    }
}


}
