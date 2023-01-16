package stage3.four.services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import stage3.four.modelsDTO.AllAvailableRoomSeatsDTO;
import stage3.four.repositories.RoomRepository;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final AllAvailableRoomSeatsDTO allAvailableRoomSeatsDTO;

    public RoomService(RoomRepository roomRepository, AllAvailableRoomSeatsDTO allAvailableRoomSeatsDTO) {
        this.roomRepository = roomRepository;
        this.allAvailableRoomSeatsDTO = allAvailableRoomSeatsDTO;
    }

    public ResponseEntity<Object> getAllAvailableRoomSeats(){
        return new ResponseEntity<>(allAvailableRoomSeatsDTO.createGetAllAvailableSeatsResponseObject(roomRepository), HttpStatus.OK);
    }

    public String writeJsonDataToFile() {
        String message;
        try(FileWriter file = new FileWriter("src\\main\\resources\\seats-response.json")){
            file.write(allAvailableRoomSeatsDTO.createGetAllAvailableSeatsResponseObject(roomRepository));
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
