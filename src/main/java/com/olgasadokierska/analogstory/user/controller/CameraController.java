package com.olgasadokierska.analogstory.user.controller;

import com.olgasadokierska.analogstory.user.dtos.CameraDTO;
import com.olgasadokierska.analogstory.user.dtos.UserMediaDTO;
import com.olgasadokierska.analogstory.user.exception.CustomException;
import com.olgasadokierska.analogstory.user.mapper.CameraMapper;
import com.olgasadokierska.analogstory.user.service.CameraService;
import com.olgasadokierska.analogstory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cameras")
@CrossOrigin(origins = "http://localhost:3000")
public class CameraController {

    private final UserService userService;
    private final CameraService cameraService;
    private final CameraMapper cameraMapper;

    /* @GetMapping("/user/{userId}")
     public ResponseEntity<List<Camera>> getUserCameras(@PathVariable long userId) {
         List<Camera> cameras = userService.getUserCameras(userId);
         return ResponseEntity.ok(cameras);
     }*/

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CameraDTO>> getUserCameras(@PathVariable long userId) {
        List<CameraDTO> cameras = userService.getUserCameras(userId)
                .stream()
                .map(cameraMapper::cameraToCameraDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cameras);
    }

    @GetMapping("/user/{userId}/media")
    public ResponseEntity<UserMediaDTO> getUserMedia(@PathVariable long userId) {
        UserMediaDTO userMediaDTO = userService.getUserMedia(userId);
        return ResponseEntity.ok(userMediaDTO);
    }

    //dodawanie aparatu
    @PostMapping("/add/{userId}")
    public ResponseEntity<CameraDTO> addCamera(@PathVariable Long userId, @RequestBody CameraDTO cameraDTO) {
        try {
            CameraDTO addedCamera = cameraService.addCamera(userId, cameraDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedCamera);
        } catch (CustomException e) {
            throw new CustomException("Błąd podczas dodawania kamery", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // wysatwainie na sprzedaz kamery
    @PutMapping("/setForSale/{cameraId}")
    public ResponseEntity<String> setCameraForSale(@PathVariable Long cameraId) {
        try {
            CameraDTO updatedCamera = cameraService.setCameraForSale(cameraId);
            return ResponseEntity.ok("Kamera jest teraz na sprzedaż");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
}
