package com.example.cinema.service;

import com.example.cinema.configuration.CinemaProperties;
import com.example.cinema.model.CinemaRoom;
import com.example.cinema.model.Seat;
import com.example.cinema.model.SeatInfo;
import com.example.cinema.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CinemaServiceImpl implements CinemaService {
    final SeatRepository seatRepository;
    final CinemaProperties props;

    @Override
    public CinemaRoom getCinemaRoomInfo() {
        return new CinemaRoom(
                props.getTotalRows(),
                props.getTotalColumns(),
                seatRepository.getAvailableSeats()
        );
    }

    @Override
    public SeatInfo purchase(Seat seat) {
        int price = calculatePrice(seat);
        return new SeatInfo(
                seat.getRow(),
                seat.getColumn(),
                price);
    }

    private int calculatePrice(Seat seat) {
        return seat.getRow() <= 4 ? 10 : 8;
    }
}
