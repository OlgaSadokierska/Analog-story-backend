package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.dtos.FilmDTO;
import com.olgasadokierska.analogstory.user.exception.CustomException;
import com.olgasadokierska.analogstory.user.model.Film;
import com.olgasadokierska.analogstory.user.model.Product;
import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.olgasadokierska.analogstory.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    private final FilmRepository filmRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final ProductTypeRepository productTypeRepository;



    @Autowired
    public FilmService(FilmRepository filmRepository, ProductRepository productRepository, UserRepository userRepository, ProductTypeRepository productTypeRepository) {
        this.filmRepository = filmRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productTypeRepository = productTypeRepository;
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }
//dodawanie nowej kliszy
@Transactional
public Film addFilm(long userId, Film film) {
    try {
        // Sprawdzenie, czy użytkownik istnieje
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("Użytkownik o podanym ID nie istnieje", HttpStatus.NOT_FOUND));

        // Utworzenie nowego produktu
        Product product = new Product();
        product.setProductType(productTypeRepository.getOne(2L));
        product.setDescription(null);
        product.setPrice(0.0);
        Product savedProduct = productRepository.save(product);

        // Ustawienie produktu i użytkownika dla filmu
        film.setProduct(savedProduct);
        film.setUser(user);


        // Zapisanie filmu
        return filmRepository.save(film);

    } catch (CustomException e) {
        throw e;
    } catch (Exception e) {
        throw new CustomException("Błąd podczas przetwarzania żądania", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
// wysatwainie na sprz.
/*
public FilmDTO sellFilm(Long userId, FilmDTO filmDTO) {
    try {
        // Sprawdzenie, czy użytkownik istnieje
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("Użytkownik o ID " + userId + " nie istnieje", HttpStatus.NOT_FOUND));

        // Utworzenie nowego produktu
        Product product = new Product();
        product.setProductType(productTypeRepository.getOne(1L)); // Ustawienie odpowiedniego productTypeId (1L w tym przypadku)
        product.setDescription(null);  // Ustawienie opisu na null
        product.setPrice(0.0);  // Ustawienie ceny na null
        Product savedProduct = productRepository.save(product);

        // Utworzenie filmu
        Film film = new Film();
        film.setUser(user);
        film.setIdCamera(filmDTO.getIdCamera());
        film.setLoadedFrames(filmDTO.getLoadedFrames());
        film.setIsFull(filmDTO.getIsFull());
        film.setProduct(savedProduct); // Przypisanie produktu do filmu
        film.setIsForSale(true); // Ustawienie filmu na sprzedaż

        // Zapisanie filmu
        Film savedFilm = filmRepository.save(film);

        // Mapowanie zapisanego filmu na DTO i zwrócenie odpowiedzi
        return filmMapper.filmToFilmDTO(savedFilm);

    } catch (CustomException e) {
        throw e; // Rzucamy ponownie wyjątek, aby był obsłużony przez @ExceptionHandler
    } catch (Exception e) {
        throw new CustomException("Błąd podczas przetwarzania żądania", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}*/
}

