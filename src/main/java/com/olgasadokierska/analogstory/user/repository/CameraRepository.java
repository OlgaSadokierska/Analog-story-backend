package com.olgasadokierska.analogstory.user.repository;

import com.olgasadokierska.analogstory.user.model.Camera;
import com.olgasadokierska.analogstory.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {
    List<Camera> findByUser(User user);
    List<Camera> findByIsForSale(boolean isForSale);

    boolean existsByProductId(Long productId);

    Optional<Camera> findByProductId(Long productId);
    void deleteByProductId(Long productId);
}
