package mx.edu.uteq.home4pets.annotation;


import mx.edu.uteq.home4pets.validation.PetAcceptedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PetAcceptedValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PetAccepted {
    String message() default "{adoptame.constraints.pet.accepted.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload> [] payload() default {};
}