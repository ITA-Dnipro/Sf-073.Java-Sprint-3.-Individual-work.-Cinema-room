package cinema.models.DTOs;

import lombok.*;

@Value
public class SeatPriceDTO {
    int row;
    int column;
    int price;
}
