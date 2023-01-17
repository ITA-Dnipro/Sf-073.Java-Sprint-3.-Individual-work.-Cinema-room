package cinema.models._DTOs;

import lombok.*;

@Value
public class SeatPriceDTO {
    int row;
    int column;
    int price;
}
