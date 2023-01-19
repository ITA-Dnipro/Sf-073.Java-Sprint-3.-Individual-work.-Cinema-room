package com.example.cinema.exception;

public class AlreadySoldException extends BusinessException {
    public AlreadySoldException(){
        super("The ticket has been already purchased!");
    }
}
