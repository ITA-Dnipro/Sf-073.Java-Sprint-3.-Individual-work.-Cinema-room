package stage3.four.modelsDTO;

import com.google.gson.annotations.SerializedName;

public class StatsDTO {

    @SerializedName("current_income")
    private final int currentIncome;
    @SerializedName("number_of_available_seats")
    private final int numberOfAvailableSeats;
    @SerializedName(("number_of_purchased_tickets"))
    private final int numberOfPurchasedTickets;

    public StatsDTO(int currentIncome, int numberOfAvailableSeats, int numberOfPurchasedTickets) {
        this.currentIncome = currentIncome;
        this.numberOfAvailableSeats = numberOfAvailableSeats;
        this.numberOfPurchasedTickets = numberOfPurchasedTickets;
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }

    public int getNumberOfPurchasedTickets() {
        return numberOfPurchasedTickets;
    }
}
