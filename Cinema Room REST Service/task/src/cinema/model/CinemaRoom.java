package cinema.model;

import java.util.ArrayList;
import java.util.List;

public class CinemaRoom {

    public int totalRows = 9;
    public int totalColumns = 9;
    public List<SeatInfo> seats = new ArrayList<>();


    public CinemaRoom() {
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                seats.add(new SeatInfo(i,j));
            }
        }
    }

    public CinemaRoom(List<SeatInfo> seats) {
        this.seats = seats;
    }
}
