package com.example.cinema.repository;

import com.example.cinema.configuration.CinemaProperties;
import com.example.cinema.exception.BusinessException;
import com.example.cinema.exception.OutOfBoundsException;
import com.example.cinema.model.SeatCoordinates;
import com.example.cinema.model.SeatEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@RequiredArgsConstructor
@Repository
public class SeatRepository {
    final CinemaProperties props;
    private List<SeatEntity> seats;

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
                seats.add(new SeatEntity(i, j));
            }
        }
    }

    public boolean isAvailable(SeatCoordinates seat) {
        return seats.stream()
                .filter(s -> s.getRow() == seat.getRow() &&
                        s.getColumn() == seat.getColumn())
                .anyMatch(s -> !s.isSold());
    }


    public SeatEntity sell(SeatCoordinates seat, int price) {
        var seatEntity = seats.stream()
                .filter(s -> s.getRow() == seat.getRow() &&
                        s.getColumn() == seat.getColumn())
                .findFirst().orElseThrow(OutOfBoundsException::new);
        seatEntity.setSellPrice(price);
        seatEntity.setToken(UUID.randomUUID().toString());
        return seatEntity;
    }

    public Optional<SeatEntity> getSeatByToken(String token) {
        return seats.stream()
                .filter(s -> token.equals(s.getToken()))
                .findFirst();
    }

    public void setAsAvailable(SeatCoordinates seat) {
        var seatEntity = seats.stream()
                .filter(s -> s.getRow() == seat.getRow() &&
                        s.getColumn() == seat.getColumn())
                .findFirst().orElseThrow(BusinessException::new);
        seatEntity.setToken(null);
        seatEntity.setSellPrice(null);
    }

    public int totalIncome() {
        return seats.stream()
                .map(SeatEntity::getSellPrice)
                .filter(Objects::nonNull)
                .mapToInt(i-> i)
                .sum();
    }

    public long countPurchased() {
        return seats.stream()
                .filter(s-> s.getSellPrice() != null)
                .count();
    }
}

