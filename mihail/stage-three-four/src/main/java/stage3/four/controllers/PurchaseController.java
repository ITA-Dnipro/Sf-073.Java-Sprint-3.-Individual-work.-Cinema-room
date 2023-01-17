package stage3.four.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import stage3.four.models.Seat;
import stage3.four.modelsDTO.PurchasedTicketDTO;
import stage3.four.modelsDTO.RefundTicketDTO;
import stage3.four.modelsDTO.TicketDTO;
import stage3.four.modelsDTO.TokenDTO;
import stage3.four.services.PurchaseService;

import static stage3.four.utils.Constants.POST_TICKET_PURCHASE_URL;
import static stage3.four.utils.Constants.POST_TICKET_REFUND_URL;

@RestController
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping(path = POST_TICKET_PURCHASE_URL ,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PurchasedTicketDTO> createTicketPurchase(@RequestBody Seat seat){
        return purchaseService.getPurchasedTicket(seat);
    }

    @PostMapping(path = POST_TICKET_REFUND_URL ,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RefundTicketDTO> createTicketPurchase(@RequestBody TokenDTO token){
        return purchaseService.getRefundedTicket(token);
    }
}
