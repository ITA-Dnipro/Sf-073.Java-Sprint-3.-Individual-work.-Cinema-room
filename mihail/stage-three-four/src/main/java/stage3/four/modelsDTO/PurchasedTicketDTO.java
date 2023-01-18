package stage3.four.modelsDTO;

import com.google.gson.annotations.SerializedName;

public class PurchasedTicketDTO {

    @SerializedName("token")
    private final String token;
    @SerializedName("ticket")
    private final TicketDTO ticketDTO;

    public PurchasedTicketDTO(String token, TicketDTO ticketDTO) {
        this.token = token;
        this.ticketDTO = ticketDTO;
    }

    public String getToken() {
        return token;
    }

    public TicketDTO getTicketDTO() {
        return ticketDTO;
    }
}
