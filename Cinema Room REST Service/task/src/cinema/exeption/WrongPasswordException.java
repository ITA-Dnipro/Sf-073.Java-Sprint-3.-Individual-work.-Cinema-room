package cinema.exeption;

public class WrongPasswordException extends Unauthorized {
    public WrongPasswordException(){
        super("The password is wrong!");
    }
}
