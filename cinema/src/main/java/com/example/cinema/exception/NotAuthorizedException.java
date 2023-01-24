package com.example.cinema.exception;

public class NotAuthorizedException extends BusinessException {
    public NotAuthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
