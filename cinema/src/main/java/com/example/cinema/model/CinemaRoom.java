package com.example.cinema.model;

import lombok.Value;

import java.util.List;

@Value
public class CinemaRoom {
    int totalRows ;
    int totalColumns;
    List<Seat> availableSeats;

}
