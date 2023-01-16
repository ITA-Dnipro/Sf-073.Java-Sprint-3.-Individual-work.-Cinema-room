package cinema.service;

import cinema.configuration.CinemaProperties;
import cinema.exception.AlreadySoldException;
import cinema.exception.OutOfBoundsException;
import cinema.exception.WrongPassword;
import cinema.model.*;
import cinema.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
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
    public SoldTicket purchase(Seat seat) {
        if(!seatRepository.isValid(seat)){
            throw new OutOfBoundsException();
        }

       else if(!seatRepository.isAvailable(seat))
        {
            throw new AlreadySoldException();
        }
       int price = calculatePrice(seat);
       Seat soldTicket = seatRepository.sell(seat, price);
        return new SoldTicket(soldTicket);
    }

    @Override
    public ReturnedTickedResponse returnedTicket(String token) {
        Seat seat = seatRepository.getSeatByToken(token).get();
        ReturnedTickedResponse ret = new ReturnedTickedResponse(new SeatInfo(seat.getRow(),
                seat.getColumn(),
                seat.getPrice()));
        seatRepository.setAvailable(seat);
        seatRepository.setPurchasedTickets(seatRepository.getPurchasedTickets()-1);
        return ret;
    }

    @Override
    public Statistics returnedStats(String password) {
            if(password==null||!password.equals("super_secret")){
                throw new WrongPassword();
            }
            Statistics stats = new Statistics(seatRepository.income(),
                    seatRepository.getAvailableSeats().size(),
                    seatRepository.getPurchasedTickets());
            return stats;
    }

    private int calculatePrice(Seat seat){
        return seat.getRow()<=4?10:8;
    }
    private SeatInfo addPrice(Seat seat){

        int price = calculatePrice(seat);
        return new SeatInfo(seat.getRow(),seat.getColumn(),price);
    }
}
