package cinema.rest.dto;

import cinema.domain.model.CinemaRoom;

import javax.validation.constraints.Min;
import java.util.List;

public record CinemaRoomDTO(
        @Min(1)
        int totalRows,
        @Min(1)
        int totalColumns,
        List<SeatDTO> availableSeats) {

    public static CinemaRoomDTO fromModel(CinemaRoom cinemaRoom) {
        return new CinemaRoomDTO(cinemaRoom.getTotalRows(),
                cinemaRoom.getTotalColumns(),
                SeatDTO.fromModel(cinemaRoom.getAvailableSeats()));
    }

}