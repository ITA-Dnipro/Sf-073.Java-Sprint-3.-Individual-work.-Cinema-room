package cinema.service;

import cinema.configuration.CinemaProperties;
import cinema.model.CinemaRoom;
import cinema.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
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
            seatRepository.getAvailableSeats());
    }
}
