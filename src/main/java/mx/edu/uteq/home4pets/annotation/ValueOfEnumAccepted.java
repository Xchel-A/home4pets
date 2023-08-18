package mx.edu.uteq.home4pets.annotation;


import mx.edu.uteq.home4pets.validation.ValueOfEnumAcceptedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ValueOfEnumAcceptedValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValueOfEnumAccepted {

    Class<? extends Enum<?>> enumClass();

    String message() default "{adoptame.constraints.enum.accepted.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload> [] payload() default {};

}