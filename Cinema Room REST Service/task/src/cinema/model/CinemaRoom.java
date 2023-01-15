package cinema.model;

import lombok.Value;

import java.util.List;
@Value
public class CinemaRoom {
 int total_rows;
 int total_columns;
 List<SeatInfo> available_seats;

 public CinemaRoom(int total_rows, int total_columns, List<SeatInfo> available_seats) {
  this.total_rows = total_rows;
  this.total_columns = total_columns;
  this.available_seats = available_seats;
 }

 public int getTotal_rows() {
  return total_rows;
 }

 public int getTotal_columns() {
  return total_columns;
 }

 public List<SeatInfo> getAvailable_seats() {
  return available_seats;
 }
}
