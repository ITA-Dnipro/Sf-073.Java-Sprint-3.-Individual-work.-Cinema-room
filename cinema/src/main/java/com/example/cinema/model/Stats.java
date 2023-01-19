package com.example.cinema.model;

import lombok.Value;

@Value
public class Stats {
    int currentIncome;
    int numberOfAvailableSeats;
    int numberOfPurchasedTickets;
}
