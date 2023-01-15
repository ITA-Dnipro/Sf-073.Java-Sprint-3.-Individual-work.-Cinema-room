package cinema.model;

import lombok.Value;

@Value
public class SeatInfo {

     int row;
     int column;
     int price;

    public SeatInfo(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = (row<=4?10:8);
    }


}
