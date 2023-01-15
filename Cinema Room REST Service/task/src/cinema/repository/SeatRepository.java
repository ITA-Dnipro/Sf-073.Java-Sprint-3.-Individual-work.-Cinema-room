package cinema.repository;

import cinema.configuration.CinemaProperties;
import cinema.model.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
@Repository
public class SeatRepository {
@Autowired
 CinemaProperties props;
    private List<Seat> seats;
    private List<Seat> soldSeats;
    @PostConstruct
    public void init(){

        seats = new ArrayList<>();
        for (int i = 1; i <= props.getTotalRows() ; i++) {
            for (int j = 1; j <= props.getTotalColumns() ; j++) {
                seats.add(new Seat(i,j));
            }
        }
    }
   public List<Seat> getAvailableSeats(){
        return seats;
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
        if(!(seat.getRow()>lastSeatRow
                ||seat.getColumn()>lastSeatColumn
                ||seat.getRow()<0||seat.getColumn()<0)){
            return true;
        }
        return false;
    }

}
