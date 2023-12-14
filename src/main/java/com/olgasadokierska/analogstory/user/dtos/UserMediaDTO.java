package com.olgasadokierska.analogstory.user.dtos;

import com.olgasadokierska.analogstory.user.model.Camera;
import com.olgasadokierska.analogstory.user.model.Film;
import lombok.Data;

import java.util.List;

@Data
public class UserMediaDTO {
    private List<Camera> kamery;
    private List<Film> filmy;
}
