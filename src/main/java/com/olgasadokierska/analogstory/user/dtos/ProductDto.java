package com.olgasadokierska.analogstory.user.dtos;

import jakarta.annotation.Nullable;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long userId;
    private Long productTypeId;
    private String brand;
    private String model;
    private String description;
    private double price;
}
