package com.olgasadokierska.analogstory.user.repository;

import com.olgasadokierska.analogstory.user.model.Film;
import com.olgasadokierska.analogstory.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findByUser(User user);
    boolean existsByProductId(Long productId);

    Optional<Film> findByProductId(Long productId);

    // Dodaj odpowiednie metody do zrealizowania warunków
    default List<Film> findByIsForSale(boolean isForSale) {
        return null;
    }

}
