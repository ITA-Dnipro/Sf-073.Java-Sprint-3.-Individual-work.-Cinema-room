package cinema.repository;

import cinema.model.Seat;
import cinema.model.SeatInfo;
import cinema.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@Repository
public class SeatRepository {

    int totalRows = 9;
    int totalColumns = 9;
    List<Seat> seats = new ArrayList<>();
    List<Ticket> tickets = new ArrayList<>();


    public SeatRepository() {
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                this.seats.add(new Seat(i, j));
            }
        }
    }

    public List<Seat> getAvailableSeats() {
        return seats.stream().filter(s -> !s.isBooked()).toList();
    }

    public Ticket markSeatAsBooked(Seat seat) {
        seats.stream().filter(s -> s.getRow() == seat.getRow() &&
                        s.getColumn() == seat.getColumn())
                .findFirst().ifPresent(s -> s.setBooked(true));
        Ticket ticket = new Ticket(String.valueOf(randomUUID()), new SeatInfo(seat.row, seat.column));
        tickets.add(ticket);
        return ticket;
    }

    public void markSeatAsFree(Seat seat) {
        seats.stream().filter(s -> s.getRow() == seat.getRow() &&
                        s.getColumn() == seat.getColumn())
                .findFirst().ifPresent(s -> s.setBooked(false));
    }

    public boolean isBooked(Seat seat) {
        return seats.stream()
                .filter(s -> s.getRow() == seat.getRow() && s.getColumn() == seat.getColumn())
                .anyMatch(s -> s.isBooked);
    }

    public boolean isSeatPresent(Seat seat) {
        return seat.getRow() > 0 && seat.getRow() < 10 && seat.getColumn() > 0 && seat.getColumn() < 10;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public int income() {
        int result = tickets.stream().mapToInt(ticket -> ticket.getTicket().getPrice()).sum();
        System.out.println(result);
        return result;
    }
}
