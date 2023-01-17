package stage3.four.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import stage3.four.exceptions.TicketAlreadyPurchased;
import stage3.four.exceptions.WrongToken;
import stage3.four.models.Seat;
import stage3.four.modelsDTO.PurchasedTicketDTO;
import stage3.four.modelsDTO.RefundTicketDTO;
import stage3.four.modelsDTO.TokenDTO;
import stage3.four.repositories.TicketRepository;

@Service
public class PurchaseService {

    private final TicketRepository ticketRepository;

    public PurchaseService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public ResponseEntity<PurchasedTicketDTO> getPurchasedTicket(Seat seat) throws TicketAlreadyPurchased {
        return new ResponseEntity<>(ticketRepository.postPurchaseTicket(seat), HttpStatus.OK);
    }

    public ResponseEntity<RefundTicketDTO> getRefundedTicket(TokenDTO token) throws WrongToken {
        return new ResponseEntity<>(new RefundTicketDTO(ticketRepository.getRefundedTicketByToken(token)), HttpStatus.OK);
    }
}
