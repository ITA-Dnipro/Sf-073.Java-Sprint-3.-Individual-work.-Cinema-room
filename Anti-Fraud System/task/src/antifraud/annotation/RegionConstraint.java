package antifraud.annotation;

import antifraud.validators.RegionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RegionValidator.class)
public @interface RegionConstraint {
    String message() default "{antifraud.annotation.RegionConstraint.message}";
    Class<?>[] groups() default {};
    Class<?extends Payload>[] payload() default {};
}
