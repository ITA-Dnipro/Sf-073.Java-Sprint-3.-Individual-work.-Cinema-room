package stage3.four.repositories;

import org.springframework.stereotype.Repository;
import stage3.four.configs.RoomConfig;
import stage3.four.models.Seat;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomRepository {

    private final RoomConfig roomConfig;
    private List<Seat> availableSeats;

    public RoomRepository(RoomConfig roomConfig) {

        this.roomConfig = roomConfig;
        this.availableSeats = new ArrayList<>();
        setAvailableSeats(availableSeats);
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    private void setAvailableSeats(List<Seat> availableSeats) {
        for (int i = 0; i < roomConfig.getTotalRows(); i++) {
            for (int j = 0; j < roomConfig.getTotalColumns(); j++) {
                this.availableSeats.add(new Seat(i + 1, j + 1));
            }
        }
        this.availableSeats = availableSeats;
    }

    public int getRoomRowsFromProps(){
        return roomConfig.getTotalRows();
    }

    public int getRoomColumnsFromProps(){
        return roomConfig.getTotalColumns();
    }
}
