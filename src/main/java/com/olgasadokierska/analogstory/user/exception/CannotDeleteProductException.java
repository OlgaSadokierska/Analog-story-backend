package com.olgasadokierska.analogstory.user.exception;

public class CannotDeleteProductException extends RuntimeException {

    public CannotDeleteProductException(String message) {
        super(message);
    }

    public CannotDeleteProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
