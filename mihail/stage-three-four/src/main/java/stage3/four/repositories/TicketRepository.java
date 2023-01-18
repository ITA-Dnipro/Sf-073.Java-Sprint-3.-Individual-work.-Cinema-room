package stage3.four.repositories;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Repository;
import stage3.four.exceptions.SeatsOutOfBoundsException;
import stage3.four.exceptions.TicketAlreadyPurchasedException;
import stage3.four.exceptions.WrongTokenException;
import stage3.four.models.Seat;
import stage3.four.models.Ticket;
import stage3.four.models.TicketData;
import stage3.four.models.Token;
import stage3.four.modelsDTO.PurchasedTicketDTO;
import stage3.four.modelsDTO.RefundTicketDTO;
import stage3.four.modelsDTO.TicketDTO;

import java.util.*;

@Repository
public class TicketRepository {

    private final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    private final RoomRepository roomRepository;
    private Table<String, String, TicketData> ticketTable;


    public TicketRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        this.ticketTable = initTicketRepository();
    }

    public Table<String, String, TicketData> initTicketRepository() {
        Table<String, String, TicketData> initTable = HashBasedTable.create(roomRepository.getRoomRowsFromProps(),
                        roomRepository.getRoomColumnsFromProps());
        for (Seat seat : roomRepository.getAvailableSeats()) {
            initTable.put(String.valueOf(seat.getRow()),
                    String.valueOf(seat.getColumn()),
                    new TicketData(false, new Ticket(UUID.randomUUID(), new Seat(seat.getRow(), seat.getColumn()))));
        }
        return initTable;
    }


    public String postPurchaseTicket(Seat seat) {
        TicketData ticketDataNew;
        if (isSeatExist(getTicketTable(), seat) && !isTicketForSeatFreeForPurchase(getTicketTable(), seat)) {
            TicketData ticketData = ticketTable.get(getSeatRowColumn(seat)[0], getSeatRowColumn(seat)[1]);
            ticketDataNew = new TicketData(true, ticketData.getTicket());
            ticketTable.put(getSeatRowColumn(seat)[0], getSeatRowColumn(seat)[1], ticketDataNew);
            setTicketTable(ticketTable);
        } else if (!isSeatExist(getTicketTable(), seat)) {
            throw new SeatsOutOfBoundsException();
        } else {
            throw new TicketAlreadyPurchasedException();
        }
        PurchasedTicketDTO purchasedTicketDTO = new PurchasedTicketDTO(String.valueOf(ticketDataNew.getTicket().getToken()), getTicketBySeat(seat));
        return gson.toJson(purchasedTicketDTO);
    }

    public String getRefundedTicketByToken(String token) {
        Token tokenFromJson = gson.fromJson(token, Token.class);
        TicketData ticketDataNew;
        TicketData ticketData = getTicketDataByToken(tokenFromJson.getToken());
        Seat seat = ticketData.getTicket().getSeat();
        ticketDataNew = new TicketData(false, ticketData.getTicket());
        ticketTable.put(getSeatRowColumn(seat)[0], getSeatRowColumn(seat)[1], ticketDataNew);
        setTicketTable(ticketTable);

        RefundTicketDTO refundTicketDTO = new RefundTicketDTO(new TicketDTO(ticketData.getTicket().getSeat().getRow(),
                ticketData.getTicket().getSeat().getColumn(),
                ticketData.getTicket().getPrice()));
        return gson.toJson(refundTicketDTO);
    }

    public Table<String, String, TicketData> getTicketTable() {
        return ticketTable;
    }

    private Token getTokenBySeat(Seat seat) {
        Ticket ticket = Objects.requireNonNull(ticketTable.get(getSeatRowColumn(seat)[0], getSeatRowColumn(seat)[1])).getTicket();
        return new Token(ticket.getToken().toString());
    }

    private TicketDTO getTicketBySeat(Seat seat) {
        Ticket ticket = Objects.requireNonNull(ticketTable.get(getSeatRowColumn(seat)[0], getSeatRowColumn(seat)[1])).getTicket();
        return new TicketDTO(ticket.getSeat().getRow(), ticket.getSeat().getColumn(), ticket.getPrice());
    }

    private TicketData getTicketDataByToken(String token) {
        Collection<TicketData> dataCollection = ticketTable.values();
        for (TicketData ticketData : dataCollection) {
            if (token.equals(ticketData.getTicket().getToken().toString())) {
                return ticketData;
            }
        }
        throw new WrongTokenException();
    }

    private boolean isSeatExist(Table<String, String, TicketData> ticketTable, Seat seat) {
        return (ticketTable.containsRow(String.valueOf(seat.getRow())) && ticketTable.containsColumn(String.valueOf(seat.getColumn())));
    }

    private boolean isTicketForSeatFreeForPurchase(Table<String, String, TicketData> ticketTable, Seat seat) {
        return Objects.requireNonNull(ticketTable.get(getSeatRowColumn(seat)[0], getSeatRowColumn(seat)[1])).isPurchased();
    }

    private void setTicketTable(Table<String, String, TicketData> ticketTable) {
        this.ticketTable = ticketTable;
    }

    private String[] getSeatRowColumn(Seat seat) {
        return new String[]{String.valueOf(seat.getRow()), String.valueOf(seat.getColumn())};
    }
}
