package com.olgasadokierska.analogstory.user.repository;

import com.olgasadokierska.analogstory.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    @Query("SELECT user FROM User user WHERE user.accountType.id = 3")
    List<User> findAllEmployees();

    @Query("SELECT user FROM User user WHERE user.accountType.id = 2")
    List<User> findAllUsers();

    boolean existsByLogin(String login);
}
