package com.example.antifraud.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = RegionValidator.class)
@Documented
public @interface ValidRegion {
    String message() default "{antifraud.validation.RegionValidator.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
