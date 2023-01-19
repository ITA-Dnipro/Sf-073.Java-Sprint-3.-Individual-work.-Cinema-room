package com.example.cinema.service;

import com.example.cinema.configuration.CinemaProperties;
import com.example.cinema.exception.AlreadySoldException;
import com.example.cinema.exception.OutOfBoundsException;
import com.example.cinema.exception.WrongTokenException;
import com.example.cinema.model.*;
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
                        .stream()
                        .map(this::addPrice)
                        .toList()
        );
    }

    @Override
    public SoldTicket purchase(SeatCoordinates seat) {
        if (seat.getRow() < 1 || seat.getRow() > props.getTotalRows() ||
                seat.getColumn() < 1 || seat.getColumn() > props.getTotalColumns()) {
            throw new OutOfBoundsException();
        }
        if (!seatRepository.isAvailable(seat)) {
            throw new AlreadySoldException();
        }
        int price = calculatePrice(seat);
        SeatEntity soldTicket = seatRepository.sell(seat, price);
        return new SoldTicket(soldTicket);
    }

    @Override
    public ReturnedTicketResponse returnTicket(String token) {
        SeatEntity seat = seatRepository.getSeatByToken(token)
                .orElseThrow(WrongTokenException::new);
        ReturnedTicketResponse ret = new ReturnedTicketResponse(
                new SeatInfo(seat.getRow(),
                        seat.getColumn(),
                        seat.getSellPrice())
        );
        SeatCoordinates seatCoordinates = new SeatCoordinates(seat);
        seatRepository.setAsAvailable(seatCoordinates);
        return ret;
    }

    private int calculatePrice(SeatCoordinates seat) {
        return seat.getRow() <= props.getFrontRows()
                ? props.getPrices().getFrontRows() : props.getPrices().getBackRows();
    }

    private SeatInfo addPrice(SeatCoordinates seat) {
        int price = calculatePrice(seat);
        return new SeatInfo(seat.getRow(), seat.getColumn(), price);
    }
}
