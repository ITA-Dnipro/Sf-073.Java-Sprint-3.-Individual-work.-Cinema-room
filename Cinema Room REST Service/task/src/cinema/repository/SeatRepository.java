package cinema.repository;

import cinema.configuration.CinemaProperties;
import cinema.exceprion.SeatOutOfBounceException;
import cinema.exceprion.TicketAlreadySoldException;
import cinema.exceprion.WrongSellTokenException;
import cinema.model.Seat;
import cinema.model.response.ReturnedTicketResponse;
import cinema.model.response.TicketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SeatRepository {
    private final CinemaProperties prop;
    private List<Seat> seats;

    @PostConstruct
    void init(){
        seats = new ArrayList<>();
        for (int i = 1; i <= prop.getTotalRows(); i++) {
            for (int j = 1; j <= prop.getTotalColumns(); j++) {
                seats.add(new Seat(i, j));
            }
        }
    }

    public List<Seat> getAvailableSeats(){
        var listOfSeats = seats.stream()
                    .filter(s -> !s.isSold())
                    .collect(Collectors.toList());
        listOfSeats.forEach(s -> s.setPrice(getPrice(s)));
        return listOfSeats;
    }

    public List<Seat> getPurchasedSeats(){
        return seats.stream()
                .filter(Seat::isSold)
                .collect(Collectors.toList());
    }

    public TicketResponse purchaseSeat(Seat seat){
        if (!seatAreInAvailableSeats(seat)){
            throw new SeatOutOfBounceException();
        }

        if (seatWasPurchased(seat)){
            throw new TicketAlreadySoldException();
        }

        var index = seats.indexOf(seat);
        var currSeat = seats.get(index);

        currSeat.setSellToken(UUID.randomUUID().toString());

        return new TicketResponse(currSeat);
    }

    public boolean seatAreInAvailableSeats(Seat seat){
        return seats.contains(seat);
    }

    public boolean seatWasPurchased(Seat seat){
        var index = seats.indexOf(seat);
        var currSeat = seats.get(index);
        return currSeat.isSold();
    }

    public int getPrice(Seat seat) {
        return seat.getRow() <= prop.getFrontRows()
                    ? prop.getFrontRowsPrice()
                    : prop.getBackRowsPrice();
    }

    public ReturnedTicketResponse returnTicket(String token) {
        var currSeat = findSeatByToken(token);
        if (currSeat == null){
            throw new WrongSellTokenException();
        }

        currSeat.setSellToken(null);
        return new ReturnedTicketResponse(currSeat);
    }

    private Seat findSeatByToken(String token) {
        for (Seat seat : seats) {
            if (seat.getSellToken() != null && seat.getSellToken().equals(token)) {
                return seat;
            }
        }
        return null;
    }


}
