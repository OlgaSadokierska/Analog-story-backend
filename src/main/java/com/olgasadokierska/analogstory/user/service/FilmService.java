package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.dtos.CameraDTO;
import com.olgasadokierska.analogstory.user.dtos.FilmDTO;
import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.exception.CustomException;
import com.olgasadokierska.analogstory.user.mapper.FilmMapper;
import com.olgasadokierska.analogstory.user.model.Camera;
import com.olgasadokierska.analogstory.user.model.Film;
import com.olgasadokierska.analogstory.user.model.Product;
import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.olgasadokierska.analogstory.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    private final FilmRepository filmRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CameraRepository cameraRepository;
    private final FilmMapper filmMapper;
    private final ProductTypeRepository productTypeRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository, ProductRepository productRepository, UserRepository userRepository, ProductTypeRepository productTypeRepository, CameraRepository cameraRepository,FilmMapper filmMapper) {
        this.filmRepository = filmRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productTypeRepository = productTypeRepository;
        this.cameraRepository = cameraRepository;
        this.filmMapper = filmMapper;
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }
    //dodawanie nowej kliszy
    @Transactional
    public Film addFilm(long userId, FilmDTO filmDTO, CameraDTO cameraDTO) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException("Użytkownik o podanym ID nie istnieje", HttpStatus.NOT_FOUND));


            Product product = new Product();
            product.setProductType(productTypeRepository.getOne(2L));
            product.setDescription(null);
            product.setPrice(0.0);
            product.setUser(user);
            Product savedProduct = productRepository.save(product);

            Film film = new Film();
            film.setProduct(savedProduct);
            film.setUser(user);
            film.setModel(filmDTO.getModel());
            film.setBrand(filmDTO.getBrand());
            film.setLoadedFrames(filmDTO.getLoadedFrames());
            film.setIsForSale(filmDTO.isForSale());
            film.setMaxLoaded(filmDTO.getMaxLoaded());
            film.setIsFull(filmDTO.isFull());

            Film savedFilm = filmRepository.save(film);

            return savedFilm;

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Błąd podczas przetwarzania żądania", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //przyspisywanie aparatu do kliszy
    @Transactional
    public FilmDTO assignCameraToFilm(long filmId, Long cameraId) {
        try {
            Film film = filmRepository.findById(filmId)
                    .orElseThrow(() -> new CustomException("Film o podanym ID nie istnieje", HttpStatus.NOT_FOUND));

            if (cameraId != null) {
                Camera camera = cameraRepository.findById(cameraId)
                        .orElseThrow(() -> new CustomException("Aparat o podanym ID nie istnieje", HttpStatus.NOT_FOUND));

                if (!camera.getIsForSale()) {

                    camera.setFilmLoaded(true);
                    cameraRepository.save(camera);

                    film.setIdCamera(cameraId);
                } else {
                    throw new CustomException("Nie można przypisać kamery do filmu, gdyż kamera jest na sprzedaż.", HttpStatus.FORBIDDEN);
                }
            }

            Film savedFilm = filmRepository.save(film);

            return filmMapper.filmToFilmDTO(savedFilm);

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Błąd podczas przetwarzania żądania", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //wstawanie kliszy na sptzedaz
    @Transactional
    public FilmDTO setFilmForSale(Long filmId, ProductDto productDto) {
        try {
            Film film = filmRepository.findById(filmId)
                    .orElseThrow(() -> new RuntimeException("Film not found with id: " + filmId));


            if (film.getIdCamera() != null) {
                throw new CustomException("Nie można ustawić filmu na sprzedaż. Usuń kliszę z kamery przed ustawieniem filmu na sprzedaż.", HttpStatus.FORBIDDEN);
            }

            film.setIsForSale(true);

            Product product = film.getProduct();

            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());

            filmRepository.save(film);

            return filmMapper.filmToFilmDTO(film);

        } catch (CustomException e) {

           throw e;
        } catch (Exception e) {
            throw new CustomException("Błąd podczas przetwarzania żądania", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

// usuwanie przypsianego aparatu
@Transactional
public FilmDTO removeCameraFromFilm(long filmId) {
    try {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new CustomException("Film o podanym ID nie istnieje", HttpStatus.NOT_FOUND));

        // Sprawdzenie, czy film ma przypisaną kamerę
        Long cameraId = film.getIdCamera();
        if (cameraId != null) {
            Camera camera = cameraRepository.findById(cameraId)
                    .orElseThrow(() -> new CustomException("Aparat o podanym ID nie istnieje", HttpStatus.NOT_FOUND));

            // Ustawienie filmLoaded na false
            camera.setFilmLoaded(false);
            cameraRepository.save(camera);

            // Usunięcie przypisanego ID kamery z filmu
            film.setIdCamera(null);

            Film savedFilm = filmRepository.save(film);

            return filmMapper.filmToFilmDTO(savedFilm);
        } else {
            throw new CustomException("Film nie jest przypisany do żadnej kamery.", HttpStatus.BAD_REQUEST);
        }

    } catch (CustomException e) {
        throw e;
    } catch (Exception e) {
        throw new CustomException("Błąd podczas przetwarzania żądania", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
















}
