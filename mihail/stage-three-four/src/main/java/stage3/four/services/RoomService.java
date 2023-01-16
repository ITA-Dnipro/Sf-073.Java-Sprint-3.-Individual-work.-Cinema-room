package stage3.four.services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import stage3.four.configs.RoomConfig;

import stage3.four.models.Room;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RoomService {
    private final Gson gson;
    private final RoomConfig roomConfig;


    public RoomService(RoomConfig roomConfig) {
        this.roomConfig = roomConfig;
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    public Collection<Object> createRoomResponseObject(){
        Room room = new Room(roomConfig.getTotalRows(), roomConfig.getTotalColumns());
        List<Object> collection = new ArrayList<>();
        collection.add(room);
        collection.add(room.getAvailableSeats());
        return collection;
    }


    public ResponseEntity<Object> generateRoomResponse(){
        return new ResponseEntity<>(gson.toJson(createRoomResponseObject()), HttpStatus.OK);
    }


    public String writeJsonDataToFile() {
        String message;
        try(FileWriter file = new FileWriter("src\\main\\resources\\seats-response.json")){
            file.write(gson.toJson(createRoomResponseObject()));
            file.flush();
            message = "Successfully write available seat list to file";
        }catch (IOException ex){
            message = "Write to file failed";
            ex.getCause();
            ex.printStackTrace();
        }
        return message;
    }
}
