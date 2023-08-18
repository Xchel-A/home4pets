package mx.edu.uteq.home4pets.validation;

import mx.edu.uteq.home4pets.annotation.UsernameAccepted;
import mx.edu.uteq.home4pets.entity.UserAdoptame;
import mx.edu.uteq.home4pets.service.UserAdoptameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameAcceptedValidator implements ConstraintValidator<UsernameAccepted, String> {

    @Autowired
    private UserAdoptameServiceImpl userAdoptameService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean flag = false;

        if (value != null ) {

            UserAdoptame user = userAdoptameService.findUserByUsername(value);

            if (user != null && user.getId() !=  0) {
                flag = true;
            }
        }

        return flag;
    }
}
