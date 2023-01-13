package stage.two.services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import stage.one.models.Room;
import stage.one.models.Seat;
import stage.two.models.Ticket;

@Service
public class PurchaseService {
    private final Gson gson;
    private final ReservationService reservationService;
    private final Room room;

    public PurchaseService(ReservationService reservationService, Room room) {
        this.reservationService = reservationService;
        this.room = room;

        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    public Ticket createTicketPurchaseResponseObject(Seat seat){
        if(reservationService.isAvailable(seat)){
            return new Ticket(seat);
        }
        //TODO implementation
        return null;
    }

    public ResponseEntity<Object> generateTicketPurchaseResponse(Seat seat) {
        if(seat.getRow() > room.getTotalRows() || seat.getColumn() > room.getTotalColumns()){
        return new ResponseEntity<>("error: The number of a row or a column is out of bounds!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(gson.toJson(createTicketPurchaseResponseObject(seat)), HttpStatus.OK);
    }
}
