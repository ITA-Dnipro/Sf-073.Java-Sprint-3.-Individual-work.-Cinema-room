package antifraud.services.contracts;

public interface Validator {

    void validateIp(String ip);

    void validateCardNumber(String cardNumber);
}
