package com.olgasadokierska.analogstory.user.dtos;


import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private Long productTypeId;
    private String description;
    private double price;
    private String model;
    private String brand;


}
