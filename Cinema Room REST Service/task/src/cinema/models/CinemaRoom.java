package cinema.models;

import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Value
public class CinemaRoom {
    int totalRows;
    int totalColumns;
    List<Seat> availableSeats;

}
