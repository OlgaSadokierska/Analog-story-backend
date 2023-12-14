package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.dtos.CredentialsDto;
import com.olgasadokierska.analogstory.user.dtos.SignUpDto;
import com.olgasadokierska.analogstory.user.dtos.UserDto;
import com.olgasadokierska.analogstory.user.exception.AppException;
import com.olgasadokierska.analogstory.user.mapper.UserMapper;
import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
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

}

