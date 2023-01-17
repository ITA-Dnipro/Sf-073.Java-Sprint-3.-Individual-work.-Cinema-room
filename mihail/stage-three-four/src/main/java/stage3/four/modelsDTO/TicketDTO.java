package stage3.four.modelsDTO;

import com.google.gson.annotations.SerializedName;

public class TicketDTO {

    @SerializedName("row")
    private final int ticketSeatRow;
    @SerializedName("column")
    private final int ticketSeatColumn;
    @SerializedName("price")
    private final int ticketPrice;

    public TicketDTO(int ticketSeatRow, int ticketSeatColumn, int ticketPrice) {
        this.ticketSeatRow = ticketSeatRow;
        this.ticketSeatColumn = ticketSeatColumn;
        this.ticketPrice = ticketPrice;
    }

    public int getTicketSeatRow() {
        return ticketSeatRow;
    }

    public int getTicketSeatColumn() {
        return ticketSeatColumn;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }
}
