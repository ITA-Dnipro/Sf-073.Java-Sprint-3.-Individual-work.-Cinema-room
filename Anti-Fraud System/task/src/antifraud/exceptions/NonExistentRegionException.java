package antifraud.exceptions;

public class NonExistentRegionException extends  BusinessException {
    public NonExistentRegionException() {
    }

    public NonExistentRegionException(String message) {
        super(message);
    }
}
