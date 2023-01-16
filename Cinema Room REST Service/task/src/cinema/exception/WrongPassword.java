package cinema.exception;

public class WrongPassword extends RuntimeException{
    public WrongPassword(){
        super("The password is wrong!");
    }
}
