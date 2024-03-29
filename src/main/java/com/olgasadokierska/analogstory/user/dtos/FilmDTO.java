package com.olgasadokierska.analogstory.user.dtos;
import lombok.*;

import java.util.Optional;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO {
    private Long idCamera;
    private Long idProductu;
    private int loadedFrames;
    private Boolean isFull;
    private String model;
    private String brand;
    private Boolean isForSale;
    private int maxLoaded;
    private Optional<ProductDto> productDto;
}