package cinema.models;

import cinema.models._model_DTOs.SeatPriceDTO;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;


import java.util.List;

@Slf4j
@Value
public class CinemaRoom {
    int totalRows;
    int totalColumns;
    List<SeatPriceDTO> availableSeats;

}
