package antifraud.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Checks the input value if it's available as UserRole Enum.
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = AvailableRoleValidator.class)
@Documented
public @interface AvailableRole {

    String message() default "{antifraud.validation.AvailableRole.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}