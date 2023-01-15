package cinema.model;


public class ReturnedTickedResponse {
    SeatInfo returnedTicket;

    public ReturnedTickedResponse(SeatInfo returnedTicket) {
        this.returnedTicket = returnedTicket;
    }

    public SeatInfo getReturnedTicket() {
        return returnedTicket;
    }

    public void setReturnedTicket(SeatInfo returnedTicket) {
        this.returnedTicket = returnedTicket;
    }
}
