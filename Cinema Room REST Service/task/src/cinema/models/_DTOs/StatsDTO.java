package cinema.models._DTOs;

import lombok.Value;

@Value
public class StatsDTO {
    int currentIncome;
    int numberOfAvailableSeats;
    int numberOfPurchasedTickets;

}
