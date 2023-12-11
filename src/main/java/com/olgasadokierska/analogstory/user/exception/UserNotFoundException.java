package com.olgasadokierska.analogstory.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public UserNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.NOT_FOUND;
    }

}
