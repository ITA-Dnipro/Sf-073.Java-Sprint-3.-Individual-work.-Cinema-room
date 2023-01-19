package com.example.cinema.model;

import lombok.Value;

@Value
public class SoldTicket {
    String token;
    SeatInfo ticket;

    public SoldTicket(SeatEntity seat) {
        this.token = seat.getToken();
        this.ticket = new SeatInfo(seat.getRow(), seat.getColumn(),seat.getSellPrice());
    }
}
