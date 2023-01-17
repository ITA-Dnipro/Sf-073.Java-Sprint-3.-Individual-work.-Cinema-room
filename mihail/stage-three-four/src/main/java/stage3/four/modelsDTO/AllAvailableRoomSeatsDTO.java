package stage3.four.modelsDTO;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import stage3.four.models.Room;
import stage3.four.repositories.RoomRepository;

@Component
public class AllAvailableRoomSeatsDTO {

    private final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    public String createGetAllAvailableSeatsResponseObject(RoomRepository roomRepository) {
        return gson.toJson(new Room(roomRepository.getRoomRowsFromProps(),
                roomRepository.getRoomColumnsFromProps(),
                roomRepository.getAvailableSeats()));
    }
}
