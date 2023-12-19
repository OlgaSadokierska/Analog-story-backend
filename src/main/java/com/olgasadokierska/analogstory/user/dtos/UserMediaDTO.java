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

    private List<Camera> cameras;
    private List<Film> films;

    @Getter
    private List<Reservation> reservations;

    public void setKamery(List<Camera> films) {
            this.cameras = cameras;
        }

    public void setFilmy(List<Film> films) {
            this.films = films;
        }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    }



