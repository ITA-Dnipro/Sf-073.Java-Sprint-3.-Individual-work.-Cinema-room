package stage3.four.modelsDTO;

public class PurchasedTicketDTO {

    private final TokenDTO tokenDTO;
    private final TicketDTO ticketDTO;

    public PurchasedTicketDTO(TokenDTO tokenDTO, TicketDTO ticketDTO) {
        this.tokenDTO = tokenDTO;
        this.ticketDTO = ticketDTO;
    }

    public TokenDTO getTokenDTO() {
        return tokenDTO;
    }

    public TicketDTO getTicketDTO() {
        return ticketDTO;
    }
}
