package cinema.service;

import cinema.configuration.CinemaProperties;
import cinema.model.CinemaRoom;
import cinema.model.Seat;
import cinema.model.request.SeatRequest;
import cinema.model.response.ReturnedTicketResponse;
import cinema.model.response.StatisticResponse;
import cinema.model.response.TicketResponse;
import cinema.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {
    final SeatRepository seatRepository;
    final CinemaProperties props;

    @Override
    public CinemaRoom getCinemaInfo() {
        return new CinemaRoom(props.getTotalRows(),
                props.getTotalColumns(),
                seatRepository.getAvailableSeats());
    }

    @Override
    public TicketResponse purchaseSeat(Seat seat) {
        return seatRepository.purchaseSeat(seat);
    }

    @Override
    public ReturnedTicketResponse returnTicket(String token) {
        return seatRepository.returnTicket(token);
    }

    @Override
    public Seat mapSeatDTOToEntity(SeatRequest seatDTO){
        var seat = new Seat(seatDTO.getRow(),seatDTO.getColumn());
        seat.setPrice(seatRepository.getPrice(seat));
        return seat;
    }

    @Override
    public boolean authorizationIsValid(String password) {
        return props.getAdminPassword()!=null && props.getAdminPassword().equals(password);
    }

    @Override
    public StatisticResponse getStats() {
        var availableSeats = seatRepository.getAvailableSeats();
        var purchasedSeats = seatRepository.getPurchasedSeats();
        var income = calculateIncome(purchasedSeats);

        return new StatisticResponse(income,availableSeats.size(),purchasedSeats.size());
    }

    private int calculateIncome(List<Seat> purchasedSeats) {
        int income = 0;
        for (Seat seat : purchasedSeats) {
            income += seat.getPrice();
        }
        return income;
    }
}
