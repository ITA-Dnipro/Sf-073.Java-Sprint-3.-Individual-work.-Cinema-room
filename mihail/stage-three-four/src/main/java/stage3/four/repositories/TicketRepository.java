package stage3.four.repositories;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import org.springframework.stereotype.Repository;
import stage3.four.models.Seat;
import stage3.four.models.Ticket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class TicketRepository {

    public Table<String, String, List<Object>> initTicketRepository(RoomRepository roomRepository) {
        Table<String, String, List<Object>> ticketTable = HashBasedTable.create(roomRepository.getRoomRowsFromProps(),
                roomRepository.getRoomColumnsFromProps());
        for (Seat seat: roomRepository.getAvailableSeats()){
            ticketTable.put(String.valueOf(seat.getRow()),
                    String.valueOf(seat.getColumn()),
                            new ArrayList<>(Arrays.asList(false, new Ticket(UUID.randomUUID(), new Seat(seat.getRow(), seat.getColumn())))));
        }
        return ticketTable;
    }

}
