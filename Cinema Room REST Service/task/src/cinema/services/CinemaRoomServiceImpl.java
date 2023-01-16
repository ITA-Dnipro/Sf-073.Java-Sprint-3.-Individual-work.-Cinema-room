package cinema.services;

import cinema.config.CinemaProperties;
import cinema.exceptions.AlreadySoldException;
import cinema.exceptions.OutOfBoundsException;
import cinema.models.CinemaRoom;
import cinema.models.DTOs.SeatPriceDTO;
import cinema.models.DTOs.SeatCoordinates;
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
                        .stream()
                        .map(this::convertSeatWithPrice)
                        .toList()
        );
    }

    @Override
    public SeatPriceDTO purchase(SeatCoordinates seat) {
        if ((seat.getRow() < 1 || seat.getRow() > properties.getTotalRows()) ||
                (seat.getColumn() < 1 || seat.getColumn() > properties.getTotalColumns())) {
            throw new OutOfBoundsException();
        }
        if (!seatRepository.isAvailable(seat)) {
            throw new AlreadySoldException();
        }
        seatRepository.markAsSold(seat);
        return convertSeatWithPrice(seat);
    }

    private int calculatedPrice(SeatCoordinates seat) {
        return seat.getRow() <= properties.getFrontRows()
                ?
                properties.getPrices().getFrontRows() : properties.getPrices().getBackRows();
    }

    private SeatPriceDTO convertSeatWithPrice(SeatCoordinates seat) {
        int price = calculatedPrice(seat);
        return new SeatPriceDTO(
                seat.getRow(),
                seat.getColumn(),
                price
        );
    }
}
