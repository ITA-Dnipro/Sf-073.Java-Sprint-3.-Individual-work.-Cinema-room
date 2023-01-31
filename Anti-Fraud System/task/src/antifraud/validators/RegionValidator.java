package antifraud.validators;

import antifraud.annotation.RegionConstraint;
import antifraud.model.Region;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class RegionValidator implements ConstraintValidator<RegionConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for (Region region : Region.values()) {
            if (region.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
