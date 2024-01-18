package com.olgasadokierska.analogstory.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDTO {
    private LocalDateTime reservationDate;
    private LocalDateTime expirationDate;
}
