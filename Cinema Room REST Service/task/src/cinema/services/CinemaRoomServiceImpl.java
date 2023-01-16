package cinema.services;

import cinema.config.CinemaProperties;
import cinema.exceptions.AlreadySoldException;
import cinema.exceptions.OutOfBoundsException;
import cinema.exceptions.WrongTokenException;
import cinema.models.CinemaRoom;
import cinema.models._model_DTOs.ReturnedTicketDTO;
import cinema.models._model_DTOs.SeatPriceDTO;
import cinema.models._model_DTOs.SeatCoordinates;
import cinema.models._model_DTOs.SeatTokenDTO;
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
                        .stream()
                        .map(this::convertSeatWithPrice)
                        .toList()
        );
    }

    @Override
    public SeatTokenDTO purchase(SeatCoordinates seat) {
        if ((seat.getRow() < 1 || seat.getRow() > properties.getTotalRows()) ||
                (seat.getColumn() < 1 || seat.getColumn() > properties.getTotalColumns())) {
            throw new OutOfBoundsException();
        }
        if (!seatRepository.isAvailable(seat)) {
            throw new AlreadySoldException();
        }
        int price = calculatedPrice(seat);
        Seat soldTicket = seatRepository.sell(seat, price);
        return new SeatTokenDTO(soldTicket);
    }

    @Override
    public ReturnedTicketDTO returnTicket(String token) {
        Seat seat = seatRepository.getSeatByToken(token).orElseThrow(WrongTokenException::new);
        ReturnedTicketDTO ticket = new ReturnedTicketDTO(new SeatPriceDTO(seat.getRow(), seat.getColumn(), seat.getSellPrice()));
        SeatCoordinates seatCoordinates = new SeatCoordinates(seat);
        seatRepository.setAsAvailable(seatCoordinates);
        return ticket;
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
