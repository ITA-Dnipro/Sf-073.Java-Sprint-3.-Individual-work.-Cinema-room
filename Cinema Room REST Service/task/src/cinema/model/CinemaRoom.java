package cinema.model;

import lombok.Value;

import java.util.List;
@Value
public class CinemaRoom {
 int totalRows;
 int totalColumns;
 List<SeatInfo> availableSeats;

 public CinemaRoom(int totalRows, int totalColumns, List<SeatInfo> availableSeats) {
  this.totalRows = totalRows;
  this.totalColumns = totalColumns;
  this.availableSeats = availableSeats;
 }

 public int getTotalRows() {
  return totalRows;
 }

 public int getTotalColumns() {
  return totalColumns;
 }

 public List<SeatInfo> getAvailableSeats() {
  return availableSeats;
 }
}
