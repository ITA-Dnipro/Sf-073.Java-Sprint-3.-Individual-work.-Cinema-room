package com.example.cinema.exception;

public class NotAuthorizedException extends BusinessException {
    public NotAuthorizedException() {
        super("The password is wrong!");
    }
}
