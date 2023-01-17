package stage3.four.repositories;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Repository;
import stage3.four.models.Seat;
import stage3.four.models.Ticket;
import stage3.four.modelsDTO.PurchasedTicketDTO;
import stage3.four.modelsDTO.TicketDTO;
import stage3.four.modelsDTO.TokenDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class TicketRepository {

    private final RoomRepository roomRepository;
    private Table<String, String, List<Object>> ticketTable;


    public TicketRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        this.ticketTable = initTicketRepository(roomRepository);
    }

    public Table<String, String, List<Object>> initTicketRepository(RoomRepository roomRepository) {

        Table<String, String, List<Object>> initTable = HashBasedTable.create(roomRepository.getRoomRowsFromProps(),
                roomRepository.getRoomColumnsFromProps());
        for (Seat seat: roomRepository.getAvailableSeats()){
            initTable.put(String.valueOf(seat.getRow()),
                    String.valueOf(seat.getColumn()),
                            new ArrayList<>(Arrays.asList(false, new Ticket(UUID.randomUUID(), new Seat(seat.getRow(), seat.getColumn())))));
        }
        return initTable;
    }

    public boolean isSeatExist(Table<String, String, List<Object>> ticketTable, Seat seat){
        return (ticketTable.containsRow(String.valueOf(seat.getRow())) && ticketTable.containsColumn(String.valueOf(seat.getColumn())));
    }

    public boolean isTicketForSeatPurchased(Table<String, String, List<Object>> ticketTable, Seat seat){
        return (boolean)ticketTable.get(getSeatRowColumn(seat)[0], getSeatRowColumn(seat)[1]).get(0);
    }


    public Table<String, String, List<Object>> getTicketTable() {
        return ticketTable;
    }

    public void setTicketTable(Table<String, String, List<Object>> ticketTable, Seat seat) {

        this.ticketTable = ticketTable;
    }

    private String[] getSeatRowColumn(Seat seat){
        return new String[]{String.valueOf(seat.getRow()), String.valueOf(seat.getColumn())};
    }

    public PurchasedTicketDTO postPurchaseTicket(Seat seat) {
        return new PurchasedTicketDTO(new TokenDTO("token"), new TicketDTO(2, 3, 10));
    }

    public TicketDTO getRefundedTicketByToken(TokenDTO token) {
        return new TicketDTO(5, 5, 50);
    }
}
