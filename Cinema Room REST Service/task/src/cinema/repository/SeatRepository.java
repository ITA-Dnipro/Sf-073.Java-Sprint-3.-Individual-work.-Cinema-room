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
}
