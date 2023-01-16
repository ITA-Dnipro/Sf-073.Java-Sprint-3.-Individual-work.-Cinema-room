package stage3.four.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import stage3.four.models.Room;
import stage3.four.services.RoomService;

import static stage3.four.utils.Constants.GET_ROOM_SEATS_URL;

@RestController
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping(path = GET_ROOM_SEATS_URL , produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getAvailableRoomSeats(){
        return roomService.generateRoomResponse();
    }
}
