package com.olgasadokierska.analogstory.user.service;

import com.olgasadokierska.analogstory.user.dtos.CameraDTO;
import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.exception.CustomException;
import com.olgasadokierska.analogstory.user.mapper.CameraMapper;
import com.olgasadokierska.analogstory.user.model.*;
import com.olgasadokierska.analogstory.user.repository.*;
import jakarta.transaction.Transactional;
import com.olgasadokierska.analogstory.user.model.Camera;
import com.olgasadokierska.analogstory.user.repository.CameraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.olgasadokierska.analogstory.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CameraService {

    private final CameraRepository cameraRepository;
    private final CameraMapper cameraMapper;
    private final ProductTypeRepository productTypeRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FilmRepository filmRepository;
    private final ReservationRepository reservationRepository;
    private final ProductService productService;
    private final CartRepository cartRepository;

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


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("Użytkownik o ID " + userId + " nie istnieje", HttpStatus.NOT_FOUND));

        Product product = new Product();
        product.setDescription(null);  // Ustawienie opisu na null
        product.setPrice(0.0);  // Ustawienie ceny na null
        product.setUser(user);
        product.setModel(cameraDTO.getModel());
        product.setBrand(cameraDTO.getBrand());
        Product savedProduct = productRepository.save(product);

        Camera camera = new Camera();
        camera.setUser(user);
        camera.setModel(cameraDTO.getModel());
        camera.setBrand(cameraDTO.getBrand());
        camera.setFilmLoaded(false);
        camera.setIsForSale(false);
        camera.setProduct(savedProduct);

        Camera savedCamera = cameraRepository.save(camera);

        return cameraMapper.cameraToCameraDTO(savedCamera);

    }

    @Transactional
    public CameraDTO setCameraForSale(Long cameraId, ProductDto productDto) {
        try {

            Camera camera = cameraRepository.findById(cameraId)
                    .orElseThrow(() -> new RuntimeException("Nie znaleziono aparatu od id: " + cameraId));

            if (camera.getFilmLoaded()) {
                throw new CustomException("Załadowany film. Usuń go przed ustawieniem kamery na sprzedaż.", HttpStatus.BAD_REQUEST);
            }

            Product product = camera.getProduct();

            camera.setIsForSale(true);

            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());


            cameraRepository.save(camera);

            return cameraMapper.cameraToCameraDTO(camera);

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Błąd podczas przetwarzania żądania", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//usuwanie aparatu
@Transactional
public void deleteCameraAndProduct(Long cameraId) {
    try {
        Camera camera = cameraRepository.findById(cameraId)
                .orElseThrow(() -> new CustomException("Nie znaleziono aparat o id: " + cameraId, HttpStatus.NOT_FOUND));

        if (camera.getFilmLoaded()) {
            throw new CustomException("Nie można usunąć aparatu, który ma załoadowany film.", HttpStatus.FORBIDDEN);
        }

        Product product = camera.getProduct();
        List<Reservation> reservations = reservationRepository.findByProductId(product.getId());

        if (!reservations.isEmpty()) {
            throw new CustomException("Nie można usunąć aparatu, który jest zarezerwowany", HttpStatus.FORBIDDEN);
        }

        productRepository.deleteById(product.getId());

        cameraRepository.deleteById(cameraId);

    } catch (CustomException e) {
        throw e;
    } catch (Exception e) {
        throw new CustomException("Błąd podczas przetwarzania żądania", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
// update aparatu
@Transactional
public CameraDTO updateCameraDetails(Long cameraId, CameraDTO updatedCameraDTO) {
    try {
        Camera camera = cameraRepository.findById(cameraId)
                .orElseThrow(() -> new CustomException("Nie znaleziono aparatu o id: " + cameraId, HttpStatus.NOT_FOUND));

        // Aktualizuj pola kamery
        camera.setModel(updatedCameraDTO.getModel());
        camera.setBrand(updatedCameraDTO.getBrand());

        // Pobierz ProductDto z CameraDTO
        ProductDto existingProductDto = updatedCameraDTO.getProductDto().orElse(null);

        if (existingProductDto != null) {
            // Jeśli ProductDto jest dostępny, zaktualizuj produkt związany z kamerą
            ProductDto productDto = new ProductDto();
            productDto.setBrand(updatedCameraDTO.getBrand());
            productDto.setModel(updatedCameraDTO.getModel());

            // Ustaw opis tylko, jeśli jest podany w zaktualizowanym ProductDto
            if (existingProductDto.getDescription() != null) {
                productDto.setDescription(existingProductDto.getDescription());
            } else {
                productDto.setDescription(camera.getProduct().getDescription());
            }

            // Ustaw cenę tylko, jeśli jest podana w zaktualizowanym ProductDto
            if (existingProductDto.getPrice() != 0.0) {
                productDto.setPrice(existingProductDto.getPrice());
            } else {
                productDto.setPrice(camera.getProduct().getPrice());
            }

            productService.updateProduct(camera.getProduct().getId(), productDto);
        }

        // Zapisz zmiany w bazie danych
        cameraRepository.save(camera);

        return cameraMapper.cameraToCameraDTO(camera);

    } catch (CustomException e) {
        throw e;
    } catch (Exception e) {
        throw new CustomException("Błąd podczas przetwarzania żądania", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}







}

