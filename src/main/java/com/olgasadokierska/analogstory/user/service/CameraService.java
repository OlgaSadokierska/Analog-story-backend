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
        product.setProductType(productTypeRepository.getOne(1L)); // Ustawienie odpowiedniego productTypeId (1L w tym przypadku)
        product.setDescription(null);  // Ustawienie opisu na null
        product.setPrice(0.0);  // Ustawienie ceny na null
        product.setUser(user);
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
                    .orElseThrow(() -> new RuntimeException("Camera not found with id: " + cameraId));

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
/*
    @Transactional
    public void deleteCamera(Long cameraId) {
        try {
            Camera camera = cameraRepository.findById(cameraId)
                    .orElseThrow(() -> new CustomException("Kamera o podanym ID nie istnieje", HttpStatus.NOT_FOUND));

            Film film = filmRepository.findByCameraId(camera.getId());

            if (film != null) {
                Long filmProductId = film.getProduct().getId();
                if (reservationRepository.existsByProductId(filmProductId)) {
                    throw new CustomException("Nie można usunąć kamery, ponieważ istnieją powiązane rezerwacje.", HttpStatus.BAD_REQUEST);
                }
                productService.deleteProduct(filmProductId);
            }

            Product cameraProduct = camera.getProduct();
            if (reservationRepository.existsByProductId(cameraProduct.getId())) {
                throw new CustomException("Nie można usunąć kamery, ponieważ istnieją powiązane rezerwacje.", HttpStatus.BAD_REQUEST);
            }

            cameraRepository.delete(camera);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Błąd podczas przetwarzania żądania", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

}