package com.example.cinema.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CinemaRoom {
    private int totalRows = 9;
    private int totalColumns = 9;

    private List<Seat> availableSeats;

    public CinemaRoom() {
        availableSeats = new ArrayList<>();
        for(int i = 1; i <= totalRows; i++) {
            for(int j = 1; j <= totalColumns; j++) {
                availableSeats.add(new Seat(i, j));
            }
        }
    }
}
