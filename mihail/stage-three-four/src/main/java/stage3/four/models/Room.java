package stage3.four.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Room {

    @SerializedName("total_rows")
    private int totalRows;
    @SerializedName("total_columns")
    private int totalColumns;
    @SerializedName("available_seats")
    private List<Seat> seatList;

    public Room(int totalRows, int totalColumns, List<Seat> seatList) {
        setTotalRows(totalRows);
        setTotalColumns(totalColumns);
        setSeatList(seatList);
    }

    public int getTotalRows() {
        return totalRows;
    }

    private void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    private void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    private void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }
}
