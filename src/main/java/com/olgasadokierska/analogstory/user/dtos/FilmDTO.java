package com.olgasadokierska.analogstory.user.dtos;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO {
    private Long idCamera;
    private int loadedFrames;
    private boolean isFull;
    private String model;
    private String brand;
    private boolean isForSale;
    private int maxLoaded;
}