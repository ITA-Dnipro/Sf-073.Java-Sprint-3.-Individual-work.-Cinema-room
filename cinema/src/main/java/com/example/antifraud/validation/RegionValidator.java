package com.example.antifraud.validation;



import com.example.antifraud.model.enums.Region;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class RegionValidator implements ConstraintValidator<ValidRegion, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            return Arrays.stream(Region.values())
                    .anyMatch(
                            region -> region.name().equals(value)
                    );
        } catch (Exception e) {
            return false;
        }
    }
}
