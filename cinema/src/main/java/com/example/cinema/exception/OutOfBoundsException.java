package com.example.cinema.exception;

public class OutOfBoundsException extends BusinessException {
    public OutOfBoundsException() {
        super("The number of a row or a column is out of bounds!");
    }

}
