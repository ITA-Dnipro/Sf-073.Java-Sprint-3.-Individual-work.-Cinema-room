package cinema.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Seat {
     int row;
     int column;

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
