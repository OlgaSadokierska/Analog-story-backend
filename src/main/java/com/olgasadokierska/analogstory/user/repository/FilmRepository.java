package com.olgasadokierska.analogstory.user.repository;

import com.olgasadokierska.analogstory.user.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
}
