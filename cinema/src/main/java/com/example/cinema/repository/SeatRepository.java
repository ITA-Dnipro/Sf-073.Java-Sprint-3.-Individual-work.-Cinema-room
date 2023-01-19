package com.example.cinema.repository;

import com.example.cinema.configuration.CinemaProperties;
import com.example.cinema.model.Seat;
import com.example.cinema.model.SeatCoordinates;
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


    public List<SeatCoordinates> getAvailableSeats() {
        return seats.stream()
                .filter(seat -> !seat.isSold())
                .map(SeatCoordinates::new)
                .toList();
    }

    @PostConstruct
    public void init() {
        seats = new ArrayList<>();
        for (int i = 1; i <= props.getTotalRows(); i++) {
            for (int j = 1; j <= props.getTotalColumns(); j++) {
                seats.add(new Seat(i, j, false));
            }
        }
    }

    public boolean isAvailable(SeatCoordinates seat) {
        return seats.stream()
                .filter(s -> s.getRow() == seat.getRow() &&
                        s.getColumn() == seat.getColumn())
                .anyMatch(s -> !s.isSold());
    }

    public void markAsSold(SeatCoordinates seat) {
        seats.stream()
                .filter(s -> s.getRow() == seat.getRow() &&
                        s.getColumn() == seat.getColumn())
                .filter(s -> !s.isSold())
                .findFirst()
                .ifPresent(s -> s.setSold(true));
    }
}

