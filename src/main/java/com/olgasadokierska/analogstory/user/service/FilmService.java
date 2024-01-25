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

            return filmRepository.save(film);

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Błąd podczas przetwarzania żądania", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
