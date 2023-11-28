package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.model.Camera;
import com.olgasadokierska.analogstory.user.repository.CameraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CameraService {

    private final CameraRepository cameraRepository;

    public List<Camera> getAllCameras() {
        return cameraRepository.findAll();
    }

    public Camera saveCamera(Camera camera) {
        return cameraRepository.save(camera);
    }
}