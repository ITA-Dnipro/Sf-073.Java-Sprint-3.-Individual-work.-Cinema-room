package stage.two.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import stage.one.models.Room;
import stage.one.models.Seat;
import stage.one.services.RoomService;
import stage.two.services.PurchaseService;

import static stage.two.utils.Constants.POST_PURCHASE;

@RestController
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping(path = POST_PURCHASE ,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> createTicketPurchase(@RequestBody Seat seat){
        return purchaseService.generateTicketPurchaseResponse(seat);
    }
}
