package com.olgasadokierska.analogstory.user.dtos;

import com.olgasadokierska.analogstory.user.model.Camera;
import com.olgasadokierska.analogstory.user.model.Film;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class UserMediaDTO {


        private List<Camera> kamery;
        private List<Film> filmy;


    public void setKamery(List<Camera> kamery) {
            this.kamery = kamery;
        }

    public void setFilmy(List<Film> filmy) {
            this.filmy = filmy;
        }
    }


