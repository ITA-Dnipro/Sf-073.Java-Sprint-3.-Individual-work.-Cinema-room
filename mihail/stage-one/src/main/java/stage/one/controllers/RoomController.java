package stage.one.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import stage.one.models.Room;
import stage.one.services.RoomService;

import static stage.one.utils.Constants.GET_URL;


@RestController
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping(path = GET_URL , produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getAvailableRoomSeats(){
        return roomService.generateRoomResponse(new Room(9,9));
    }
}
