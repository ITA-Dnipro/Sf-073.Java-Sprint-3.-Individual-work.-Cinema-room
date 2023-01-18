package cinema.model.response;

import lombok.Value;

@Value
public class StatisticResponse {
    int currentIncome;
    int numberOfAvailableSeats;
    int numberOfPurchasedTickets;

    public StatisticResponse(int currentIncome, int numberOfAvailableSeats, int numberOfPurchasedTickets) {
        this.currentIncome = currentIncome;
        this.numberOfAvailableSeats = numberOfAvailableSeats;
        this.numberOfPurchasedTickets = numberOfPurchasedTickets;
    }
}
