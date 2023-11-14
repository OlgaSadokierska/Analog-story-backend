package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping("")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/create")
    public User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }


}
