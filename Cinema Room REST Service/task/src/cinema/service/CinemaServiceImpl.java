package cinema.service;

import cinema.configuration.CinemaProperties;
import cinema.exception.AlreadySoldException;
import cinema.exception.OutOfBoundsException;
import cinema.model.CinemaRoom;
import cinema.model.Seat;
import cinema.model.SeatInfo;
import cinema.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CinemaServiceImpl implements CinemaService{
    @Autowired
     SeatRepository seatRepository;
    @Autowired
     CinemaProperties cinemaProperties;
    @Override
    public CinemaRoom getCinemaRoomInfo() {
        return new CinemaRoom(
            cinemaProperties.getTotalRows(),
            cinemaProperties.getTotalColumns(),
            seatRepository.getAvailableSeats()
                    .stream()
                    .map(this::addPrice).toList()
        );
    }


    @Override
    public SeatInfo purchase(Seat seat) {
        if(!seatRepository.isValid(seat)){
            throw new OutOfBoundsException();
        }
       else if(!seatRepository.isAvailable(seat))
        {
            throw new AlreadySoldException();
        }
        return addPrice(seat);
    }
    private int calculatePrice(Seat seat){
        return seat.getRow()<=4?10:8;
    }
    private SeatInfo addPrice(Seat seat){
        int price = calculatePrice(seat);
        return new SeatInfo(seat.getRow(),seat.getColumn(),price);
    }
}
