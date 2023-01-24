package com.example.cinema.exception;

public class WrongTokenException extends BusinessException {
    public WrongTokenException(String errorMessage) {
        super(errorMessage);

    }
}
