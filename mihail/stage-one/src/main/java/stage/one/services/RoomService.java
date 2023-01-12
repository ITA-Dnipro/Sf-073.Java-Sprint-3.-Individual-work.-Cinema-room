package stage.one.services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import stage.one.models.Room;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RoomService {
    private final Gson gson;

    public RoomService() {
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    public Collection<Object> createRoomResponseObject(Room room){
        List<Object> collection = new ArrayList<>();
        collection.add(room);
        collection.add(room.getAvailableSeats());
        return collection;
    }


    public ResponseEntity<Object> generateRoomResponse(Room room){
        return new ResponseEntity<>(gson.toJson(createRoomResponseObject(room)), HttpStatus.OK);
    }

//    src/main/resources

    public String writeJsonDataToFile(Room room) {
        String message;
        try(FileWriter file = new FileWriter("mihail\\src\\main\\resources\\seats-response.json")){
            file.write(gson.toJson(createRoomResponseObject(room)));
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
