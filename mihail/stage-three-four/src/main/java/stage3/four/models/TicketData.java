package stage3.four.models;

public class TicketData {

    private boolean isPurchased;
    private Ticket ticket;

    public TicketData(boolean isPurchased, Ticket ticket) {
        setPurchased(isPurchased);
        setTicket(ticket);
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
