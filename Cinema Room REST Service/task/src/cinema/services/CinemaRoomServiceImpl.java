package cinema.services;

import cinema.config.CinemaProperties;
import cinema.models.CinemaRoom;
import cinema.models.DTOs.SeatPriceDTO;
import cinema.models.Seat;
import cinema.repositories.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CinemaRoomServiceImpl implements CinemaRoomService {

    private final SeatRepository seatRepository;
    private final CinemaProperties properties;

    @Override
    public CinemaRoom getCinemaRoomInfo() {
        return new CinemaRoom(
                properties.getTotalRows(),
                properties.getTotalColumns(),
                seatRepository.getAvailableSeats()
        );
    }

    @Override
    public SeatPriceDTO purchase(Seat seat) {
        int price = calculatedPrice(seat);
        return new SeatPriceDTO(
                seat.getRow(),
                seat.getColumn(),
                price
        );
    }

    private int calculatedPrice(Seat seat) {
        return seat.getRow() <= properties.getFrontRows()
                ?
                properties.getPrices().getFrontRows() : properties.getPrices().getBackRows();
    }
}
