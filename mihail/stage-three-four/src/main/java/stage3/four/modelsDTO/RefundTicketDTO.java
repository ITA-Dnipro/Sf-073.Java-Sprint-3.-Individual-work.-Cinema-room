package stage3.four.modelsDTO;

import com.google.gson.annotations.SerializedName;

public class RefundTicketDTO {

    @SerializedName("returned_ticket")
    private final TicketDTO ticketDTO;

    public RefundTicketDTO(TicketDTO ticketDTO) {
        this.ticketDTO = ticketDTO;
    }

    public TicketDTO getTicketDTO() {
        return ticketDTO;
    }
}
