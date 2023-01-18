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
        return false;
    }

    @Override
    public StatisticResponse getStats() {
        return null;
    }
}
