package stage3.four.models;

public class Seat {

    int row;
    int column;

    public Seat(int row, int column) {
        setRow(row);
        setColumn(column);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    private void setRow(int row) {
        this.row = row;
    }

    private void setColumn(int column) {
        this.column = column;
    }

}
