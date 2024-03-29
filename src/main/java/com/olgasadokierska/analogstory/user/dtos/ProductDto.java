package com.olgasadokierska.analogstory.user.dtos;

import jakarta.annotation.Nullable;
import lombok.*;
import java.util.Optional;
import java.util.Optional;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @Nullable
    private Long id;
    private Long productTypeId;
    private Long userId;
    private String description;
    private double price;
    private String model;
    private String brand;

}
