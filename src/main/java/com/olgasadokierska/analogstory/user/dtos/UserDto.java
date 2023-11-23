package com.olgasadokierska.analogstory.user.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String login;

    private Boolean isAdmin;

    private String password;

    private String token;

}
