package cinema.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ReturnTicketRequest {
    public String token;

    public ReturnTicketRequest() {
    }

    public ReturnTicketRequest(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ReturnTicketRequest{" +
                "token='" + token + '\'' +
                '}';
    }
}
