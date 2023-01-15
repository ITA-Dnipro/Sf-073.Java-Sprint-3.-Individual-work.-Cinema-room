package cinema.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Seat {
     int row;
     int column;
     int price;
     String token;
     public Seat(){

     }

     public Seat(int row, int column, int price, String token) {
          this.row = row;
          this.column = column;
          this.price = price;
          this.token = token;
     }


     public Seat(int price, String token) {
          this.price = price;
          this.token = token;
     }

     public int getPrice() {
          return price;
     }

     public void setPrice(int price) {
          this.price = price;
     }

     public String getToken() {
          return token;
     }

     public void setToken(String token) {
          this.token = token;
     }

     public Seat(int row, int column) {
          this.row = row;
          this.column = column;
     }

     public int getRow() {
          return row;
     }

     public void setRow(int row) {
          this.row = row;
     }

     public int getColumn() {
          return column;
     }

     public void setColumn(int column) {
          this.column = column;
     }
}
