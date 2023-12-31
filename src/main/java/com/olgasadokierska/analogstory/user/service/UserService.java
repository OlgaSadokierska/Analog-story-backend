package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.dtos.CredentialsDto;
import com.olgasadokierska.analogstory.user.dtos.SignUpDto;
import com.olgasadokierska.analogstory.user.dtos.UserDto;
import com.olgasadokierska.analogstory.user.dtos.UserMediaDTO;
import com.olgasadokierska.analogstory.user.exception.AppException;
import com.olgasadokierska.analogstory.user.exception.UserNotFoundException;
import com.olgasadokierska.analogstory.user.mapper.UserMapper;
import com.olgasadokierska.analogstory.user.model.*;
import com.olgasadokierska.analogstory.user.repository.CameraRepository;
import com.olgasadokierska.analogstory.user.repository.FilmRepository;
import com.olgasadokierska.analogstory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CameraRepository cameraRepository;
    private final FilmRepository filmRepository;

    public UserDto register(SignUpDto signUpDto) {
        String login = signUpDto.getLogin();
        if (userRepository.existsByLogin(login)) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(signUpDto);

        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public UserDto findById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public UserDto login(CredentialsDto credentialsDto) {
        String login = credentialsDto.getLogin();
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        userRepository.delete(user);
    }

    //wyświetlanie samego aparatu
    public List<Camera> getUserCameras(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return cameraRepository.findByUser(user);
        } else {
            throw new UserNotFoundException("Użytkownik o ID " + userId + " nie istnieje");
        }
    }

    // wyswietlanie samych klisz
    public List<Film> getUserFilms(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return filmRepository.findByUser(user);
        } else {
            throw new UserNotFoundException("Użytkownik o ID " + userId + " nie istnieje");
        }
    }

    //wyswietlanie klisz i aparatu wraz z informacjami o produkcie
    public UserMediaDTO getUserMedia(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Camera> kamery = cameraRepository.findByUser(user);
            List<Film> filmy = filmRepository.findByUser(user);

            for (Camera camera : kamery) {
                Product product = camera.getProduct();
                if (product != null) {
                    System.out.println("Informacje o produkcie dla kamery " + camera.getId() + ": " + product.getDescription() + ", Cena: " + product.getPrice());
                }
            }

            for (Film film : filmy) {
                Product product = film.getProduct();

                if (product != null) {
                    System.out.println("Informacje o produkcie dla filmu " + film.getId() + ": " + product.getDescription() + ", Cena: " + product.getPrice());
                }
            }

            UserMediaDTO userMediaDTO = new UserMediaDTO();
            userMediaDTO.setKamery(kamery);
            userMediaDTO.setFilmy(filmy);

            return userMediaDTO;
        } else {
            throw new UserNotFoundException("Użytkownik o ID " + userId + " nie istnieje");
        }
    }

    @Transactional
    public Integer[] findUserDataByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("User not found for email: " + email, HttpStatus.NOT_FOUND));

        Integer[] userData = {Integer.parseInt(user.getId().toString()), Integer.parseInt(user.getAccountType().getId().toString())};
        return userData;
    }

    @Transactional
    public UserDto updateUser(Long userId, SignUpDto signUpDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found", new Throwable()));

        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setEmail(signUpDto.getEmail());
        user.setLogin(signUpDto.getLogin());
        user.setPhone(signUpDto.getPhone());

        if (signUpDto.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
            user.setPassword(encodedPassword);
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toUserDto(updatedUser);
    }

    public List<UserDto> findAllEmployees() {
        List<User> employees = userRepository.findAllEmployees();

        return userMapper.toUserDtoList(employees);
    }

    public List<UserDto> findAllUsers() {
        List<User> employees = userRepository.findAllUsers();

        return userMapper.toUserDtoList(employees);
    }

}

