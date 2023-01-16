package cinema.repository;

import cinema.configuration.CinemaProperties;
import cinema.exception.WrongTokenException;
import cinema.model.Seat;
import cinema.model.SeatInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SeatRepository {
@Autowired
 CinemaProperties props;
    private List<Seat> seats;
    private List<Seat> soldSeats;
    private int purchasedTickets;
    @PostConstruct
    public void init(){

        seats = new ArrayList<>();
        soldSeats = new ArrayList<>();
        for (int i = 1; i <= props.getTotalRows() ; i++) {
            for (int j = 1; j <= props.getTotalColumns() ; j++) {
                seats.add(new Seat(i,j));
            }
        }
    }
   public List<Seat> getAvailableSeats(){
        return seats;
    }
    public List<Seat> getSoldSeats(){
        return soldSeats;
    }

    public int getPurchasedTickets() {
        return purchasedTickets;
    }
    public int income(){
        return  soldSeats.stream().mapToInt(Seat::getPrice).sum();
    }

    public void setPurchasedTickets(int purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }

    public boolean isAvailable(Seat seat) {
        for (int i = 0; i < getAvailableSeats().size(); i++) {
            if(getAvailableSeats().get(i).getRow()==seat.getRow()&&getAvailableSeats().get(i).getColumn()==seat.getColumn()){
                seats.remove(seats.get(i));
                return true;
            }
        }
        return false;
    }
    public boolean isValid(Seat seat){
        int lastSeatRow = getAvailableSeats().get(getAvailableSeats().size()-1).getRow();
        int lastSeatColumn = getAvailableSeats().get(getAvailableSeats().size()-1).getColumn();
        if(!(seat.getRow()>=10
                ||seat.getColumn()>=10
                ||seat.getRow()<0||seat.getColumn()<0)){
            return true;
        }
        return false;
    }

    public Optional<Seat> getSeatByToken(String token) {
       var seat = soldSeats.stream().filter(s-> token.equals(s.getToken())).findFirst();
       if(seat.isPresent()){
           return seat;
       }
       else{
           throw new WrongTokenException();
       }
    }

    public void setAvailable(Seat seat) {
        seats.add(seat);
        soldSeats.remove(seat);
    }

    public Seat sell(Seat seat, int price) {
      seats.remove(seat);
      seat.setToken(UUID.randomUUID().toString());
      seat.setPrice(price);
      soldSeats.add(seat);
      purchasedTickets++;
      return seat;
    }
}
