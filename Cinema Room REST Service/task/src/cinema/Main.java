package cinema;

import lombok.Data;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

@RestController
class CinemaController {
    @Autowired
    private CinemaRoom cinemaRoom;

    @GetMapping("/seats")
    public CinemaRoom getSeats(){
        return cinemaRoom;
    }

    @PostMapping(value = "/purchase",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity purchase(@RequestBody Seat seat) {
        if(cinemaRoom.seatWasPurchased(seat)){
            return new ResponseEntity(new AppError(
                    "The ticket has been already purchased!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(!cinemaRoom.seatAreInAvailableSeats(seat)){
            return new ResponseEntity(new AppError(
                    "The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        }

        seat.setPrice(seat.calculatePrice());
        cinemaRoom.purchaseSeat(seat);
        return new ResponseEntity(seat,
                HttpStatus.OK);
    }
}

@Component
class CinemaRoom {
    public int total_rows = 9;
    public int total_columns = 9;
    public List<Seat> available_seats = new ArrayList<>();
    private final List<Seat> purchased_seats = new ArrayList<>();

    public CinemaRoom() {
        for (int i = 1; i <= total_rows; i++) {
            for (int j = 1; j <= total_columns; j++) {
                available_seats.add(new Seat(i, j));
            }
        }
    }

    public void purchaseSeat(Seat seat){
        if (seatAreInAvailableSeats(seat) && !seatWasPurchased(seat)) {
            purchased_seats.add(seat);
            available_seats.remove(seat);
        }
    }

    public boolean seatAreInAvailableSeats(Seat seat){
        return available_seats!=null && available_seats.contains(seat);
    }

    public boolean seatWasPurchased(Seat seat){
        return purchased_seats.contains(seat);
    }
}

@Data
class Seat {
    public int row;
    public int column;
    public int price;

    public Seat() {

    }

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = calculatePrice();
    }

    public int calculatePrice() {
        return row < 4? 10:8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat)) return false;
        Seat seat = (Seat) o;
        return getRow() == seat.getRow() && getColumn() == seat.getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn());
    }
}


@Value
class AppError {
    public String error;

    public AppError(String msg) {
        this.error = msg;
    }
}
