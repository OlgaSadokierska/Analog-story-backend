package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.exception.CustomException;
import com.olgasadokierska.analogstory.user.model.Camera;
import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.repository.CameraRepository;
import com.olgasadokierska.analogstory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cameras")
@CrossOrigin(origins = "http://localhost:3000")
public class CameraController {
    private final UserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Camera>> getUserCameras(@PathVariable long userId) {
        List<Camera> cameras = userService.getUserCameras(userId);
        return ResponseEntity.ok(cameras);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }


}
