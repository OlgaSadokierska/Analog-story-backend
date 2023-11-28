package com.olgasadokierska.analogstory.user.repository;

import com.olgasadokierska.analogstory.user.model.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {
}
