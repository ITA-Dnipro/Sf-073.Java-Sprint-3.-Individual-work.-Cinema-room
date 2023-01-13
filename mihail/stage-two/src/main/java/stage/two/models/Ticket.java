package stage.two.models;

import stage.one.models.Seat;

public class Ticket {

    private Seat seat;
    private int price;

    public Ticket(Seat seat) {
        this.seat = seat;
        setPrice();
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public int getPrice() {
        return price;
    }

    private void setPrice() {
        if(this.seat.getRow() <= 4){
           this.price = 10;
        }else{
            this.price = 8;
        }
    }
}
