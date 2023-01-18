package stage3.four.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import stage3.four.models.Seat;
import stage3.four.repositories.TicketRepository;

@Service
public class PurchaseService {

    private final TicketRepository ticketRepository;

    public PurchaseService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public ResponseEntity<Object> getPurchasedTicket(Seat seat) {
        return new ResponseEntity<>(ticketRepository.postPurchaseTicket(seat), HttpStatus.OK);
    }

    public ResponseEntity<Object> getRefundedTicket(String token) {
        return new ResponseEntity<>(ticketRepository.getRefundedTicketByToken(token), HttpStatus.OK);
    }
}
