package cinema.model.response;

import lombok.Value;

@Value
public class StatisticResponse {
    int currentIncome;
    int numberOfAvailableSeats;
    int numberOfPurchasedTickets;
}
