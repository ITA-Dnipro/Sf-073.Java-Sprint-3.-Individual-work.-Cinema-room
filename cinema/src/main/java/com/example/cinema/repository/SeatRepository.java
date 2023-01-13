package com.example.cinema.repository;

import com.example.cinema.configuration.CinemaProperties;
import com.example.cinema.model.Seat;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class SeatRepository {
    final CinemaProperties props;
    private List<Seat> seats;

    public List<Seat> getAvailableSeats() {
        return seats;
    }

    @PostConstruct
    public void init() {
        seats = new ArrayList<>();
        for (int i = 1; i <= props.getTotalRows(); i++) {
            for (int j = 1; j <= props.getTotalColumns(); j++) {
                seats.add(new Seat(i, j));
            }
        }
    }
}

