package antifraud.services;

import antifraud.services.contracts.Validator;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidatorImpl implements Validator {
    @Override
    public void validateIp(String ip) {
        InetAddressValidator validator = InetAddressValidator.getInstance();
        if (!validator.isValidInet4Address(ip)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void validateCardNumber(String cardNumber) {
        if(!LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(cardNumber)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
