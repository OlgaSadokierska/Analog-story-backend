package com.olgasadokierska.analogstory.user.dtos;

import lombok.*;

import java.util.Optional;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CameraDTO {

    private Long id;
    private Long userId;
    private Long idProductu;
    private String model;
    private String brand;
    private Boolean filmLoaded;
    private Boolean isForSale;
    private Long productId;
    private Optional<ProductDto> productDto;

}
