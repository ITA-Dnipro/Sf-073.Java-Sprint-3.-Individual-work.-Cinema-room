package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Value
public class CinemaRoom {
    int totalRows;
    int totalColumns;
    @JsonProperty("available_seats")
    List<Seat> seats;
}
