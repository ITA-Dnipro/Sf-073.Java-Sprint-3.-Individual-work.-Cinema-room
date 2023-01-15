package cinema.exeption;

public class AlreadyBookedException extends BusinessException{
    public AlreadyBookedException() {
        super("The ticket has been already purchased!");
    }
}
