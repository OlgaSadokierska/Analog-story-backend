package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.dtos.CameraDTO;
import com.olgasadokierska.analogstory.user.exception.CustomException;
import com.olgasadokierska.analogstory.user.exception.UserNotFoundException;
import com.olgasadokierska.analogstory.user.mapper.CameraMapper;
import com.olgasadokierska.analogstory.user.model.*;
import com.olgasadokierska.analogstory.user.repository.*;
import jakarta.transaction.Transactional;
import com.olgasadokierska.analogstory.user.model.Camera;
import com.olgasadokierska.analogstory.user.repository.CameraRepository;
import com.olgasadokierska.analogstory.user.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.olgasadokierska.analogstory.user.model.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CameraService {

    private final CameraRepository cameraRepository;
    private final CameraMapper cameraMapper;
    private final ProductTypeRepository productTypeRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<Camera> getAllCameras() {
        return cameraRepository.findAll();
    }

    public Camera saveCamera(Camera camera) {
        return cameraRepository.save(camera);
    }

    public CameraDTO getCameraById(Long cameraId) {
        Camera camera = cameraRepository.findById(cameraId)
                .orElseThrow(() -> new RuntimeException("Camera not found with id: " + cameraId));

        return cameraMapper.cameraToCameraDTO(camera);
    }

//dodawanie aparatu
@Transactional
public CameraDTO addCamera(Long userId, CameraDTO cameraDTO) {
    try {
        // Sprawdzenie, czy użytkownik istnieje
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("Użytkownik o ID " + userId + " nie istnieje", HttpStatus.NOT_FOUND));

        // Utworzenie nowego produktu
        Product product = new Product();
        product.setProductType(productTypeRepository.getOne(1L)); // Ustawienie odpowiedniego productTypeId (1L w tym przypadku)
        product.setDescription(null);  // Ustawienie opisu na null
        product.setPrice(0.0);  // Ustawienie ceny na null
        Product savedProduct = productRepository.save(product);

        // Utworzenie kamery
        Camera camera = new Camera();
        camera.setUser(user);
        camera.setModel(cameraDTO.getModel());
        camera.setBrand(cameraDTO.getBrand());
        camera.setFilmLoaded(false);
        camera.setIsForSale(false);
        camera.setProduct(savedProduct); // Przypisanie produktu do kamery

        // Zapisanie kamery
        Camera savedCamera = cameraRepository.save(camera);

        // Mapowanie zapisanej kamery na DTO i zwrócenie odpowiedzi
        return cameraMapper.cameraToCameraDTO(savedCamera);
}

    } catch (CustomException e) {
        throw e; // Rzucamy ponownie wyjątek, aby był obsłużony przez @ExceptionHandler
    } catch (Exception e) {
        throw new CustomException("Błąd podczas przetwarzania żądania", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @Transactional
    public CameraDTO setCameraForSale(Long cameraId) {
        try {
            // Pobranie kamery
            Camera camera = cameraRepository.findById(cameraId)
                    .orElseThrow(() -> new RuntimeException("Camera not found with id: " + cameraId));

            // Sprawdzenie, czy film jest załadowany
            if (camera.getFilmLoaded()) {
                throw new CustomException("Załadowany film. Usuń go przed ustawieniem kamery na sprzedaż.", HttpStatus.BAD_REQUEST);
            }

            // Ustawienie isForSale na true
            camera.setIsForSale(true);

            // Ustawienie opisu i ceny w tabeli Product
            Product product = camera.getProduct();
            product.setDescription("Nowy opis");  // Tutaj podajesz odpowiedni opis
            product.setPrice(100.0);  // Tutaj podajesz odpowiednią cenę

            // Zapisanie zmian w bazie danych
            cameraRepository.save(camera);

            return cameraMapper.cameraToCameraDTO(camera);

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Błąd podczas przetwarzania żądania", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



