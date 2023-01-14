package cinema.domain.service.Impl;

import cinema.domain.model.CinemaRoom;
import cinema.domain.model.Seat;
import cinema.domain.service.CinemaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRoom cinemaRoom;

    public CinemaServiceImpl() {
        List<Seat> allSeats = new ArrayList<>();
        for (int row = 1; row <= 9; row++) {
            for(int column = 1; column <= 9; column++) {
                allSeats.add(new Seat(row, column));
            }
        }
        cinemaRoom = new CinemaRoom(9, 9, allSeats);
    }

    @Override
    public CinemaRoom getAvailableSeats() {
        return cinemaRoom;
    }
}
