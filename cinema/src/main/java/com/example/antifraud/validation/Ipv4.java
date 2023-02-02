package com.example.antifraud.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Pattern(regexp =
        "^([0-1]?\\d?\\d|2[0-4]\\d|25[0-5])" +
        "(\\.([0-1]?\\d?\\d|2[0-4]\\d|25[0-5]))" +
        "{3}$")
@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@ReportAsSingleViolation
public @interface Ipv4 {
    String message() default "IP must be in ipv4!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
