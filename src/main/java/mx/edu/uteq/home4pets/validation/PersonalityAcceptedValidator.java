package mx.edu.uteq.home4pets.validation;

import mx.edu.uteq.home4pets.annotation.PersonalityAccepted;
import mx.edu.uteq.home4pets.entity.Personality;
import mx.edu.uteq.home4pets.service.PersonalityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class PersonalityAcceptedValidator  implements ConstraintValidator<PersonalityAccepted, Personality> {

    @Autowired
    private PersonalityServiceImpl personalityService;

    @Override
    public boolean isValid(Personality value, ConstraintValidatorContext context) {
        boolean flag = false;

        if (value.getId() != 0) {
            Optional<Personality> item = personalityService.findPersonalityById(value.getId());

            if (item.isPresent()) {
                flag = true;
            }
        }

        return flag;
    }
}
