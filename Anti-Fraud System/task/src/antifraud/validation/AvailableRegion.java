package antifraud.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Checks the input value if it's available as WorldRegion Enum.
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = AvailableRegionValidator.class)
@Documented
public @interface AvailableRegion {

    String message() default "{antifraud.validation.AvailableRegion.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}