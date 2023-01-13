package cinema.model;

public class Seat {

    public int row;
    public int column;
    public int price;
    private boolean isBooked = false;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        price = (row<=4?10:8);
    }
}
