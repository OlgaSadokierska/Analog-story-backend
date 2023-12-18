package com.olgasadokierska.analogstory.user.dtos;

import com.olgasadokierska.analogstory.user.model.Camera;
import com.olgasadokierska.analogstory.user.model.Film;
import com.olgasadokierska.analogstory.user.model.Reservation;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class UserMediaDTO {


        private List<Camera> kamery;
        private List<Film> filmy;
    @Getter
    private List<Reservation> reservations;


    public void setKamery(List<Camera> kamery) {
            this.kamery = kamery;
        }

    public void setFilmy(List<Film> filmy) {
            this.filmy = filmy;
        }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    }



