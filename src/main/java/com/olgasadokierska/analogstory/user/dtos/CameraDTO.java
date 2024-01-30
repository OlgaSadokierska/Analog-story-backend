package com.olgasadokierska.analogstory.user.dtos;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CameraDTO {

    private Long id;
    private Long userId;
    private String model;
    private String brand;
    private Boolean filmLoaded;
    private Boolean isForSale;
    private Long productId;

}
