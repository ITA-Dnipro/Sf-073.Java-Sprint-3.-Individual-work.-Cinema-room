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
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SeatRepository {
    private final CinemaProperties prop;
    private List<Seat> seats;
    private Map<String,Seat> purchasedSeats;

    @PostConstruct
    void init(){
        seats = new ArrayList<>();
        purchasedSeats = new HashMap<>();
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
        return new ArrayList<>(purchasedSeats.values());
    }

    public TicketResponse purchaseSeat(Seat seat){
        if (!seatAreInAvailableSeats(seat)){
            throw new SeatOutOfBounceException("The number of a row or a column is out of bounds!");
        }

        if (seatWasPurchased(seat)){
            throw new TicketAlreadySoldException("The ticket has been already purchased!");
        }

        var index = seats.indexOf(seat);
        var currSeat = seats.get(index);

        currSeat.setSellToken(UUID.randomUUID().toString());
        purchasedSeats.put(currSeat.getSellToken(),currSeat);
        return new TicketResponse(currSeat);
    }

    public boolean seatAreInAvailableSeats(Seat seat){
        return seats.contains(seat);
    }

    public boolean seatWasPurchased(Seat seat){
        var purchasedSeat = purchasedSeats.values().stream()
                .filter(currSeat -> currSeat.equals(seat))
                .findAny()
                .orElse(null);
        if (purchasedSeat != null) {
            return purchasedSeat.isSold();
        }
        return false;
    }

    public int getPrice(Seat seat) {
        return seat.getRow() <= prop.getFrontRows()
                    ? prop.getFrontRowsPrice()
                    : prop.getBackRowsPrice();
    }

    public ReturnedTicketResponse returnTicket(String token) {
        var currSeat = findSeatByToken(token);
        if (currSeat == null){
            throw new WrongSellTokenException("Wrong token!");
        }

        currSeat.setSellToken(null);
        purchasedSeats.remove(token);
        return new ReturnedTicketResponse(currSeat);
    }

    private Seat findSeatByToken(String token) {

        return purchasedSeats.get(token);
    }


}
