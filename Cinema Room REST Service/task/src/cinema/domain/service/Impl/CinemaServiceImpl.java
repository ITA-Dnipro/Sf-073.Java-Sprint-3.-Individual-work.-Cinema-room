package cinema.domain.service.Impl;

import cinema.domain.model.CinemaRoom;
import cinema.domain.model.Seat;
import cinema.domain.service.CinemaService;
import cinema.exception.AlreadyPurchasedTicketException;
import cinema.exception.SeatOutOfBoundsException;
import cinema.persistence.repository.SeatRepository;
import org.springframework.stereotype.Service;

@Service
public class CinemaServiceImpl implements CinemaService {
    private final SeatRepository seatRepository;

    public CinemaServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public CinemaRoom getCinemaRoomInfo() {
        return seatRepository.getCinemaRoom();
    }

    @Override
    public Seat purchaseTicket(Seat seat) {
        if(!seatRepository.isSeatPresent(seat)) {
            throw new SeatOutOfBoundsException(seat);
        }
       return seatRepository.save(seat)
                .orElseThrow(() -> new AlreadyPurchasedTicketException(seat));
    }

}