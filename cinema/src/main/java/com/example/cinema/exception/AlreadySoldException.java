package com.example.cinema.exception;

public class AlreadySoldException extends BusinessException {
    public AlreadySoldException(String errorMessage){
        super(errorMessage);
    }

}
