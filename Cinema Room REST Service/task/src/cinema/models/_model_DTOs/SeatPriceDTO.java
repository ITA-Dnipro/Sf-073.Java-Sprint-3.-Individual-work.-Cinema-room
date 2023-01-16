package cinema.models._model_DTOs;

import lombok.*;

@Value
public class SeatPriceDTO {
    int row;
    int column;
    int price;
}
