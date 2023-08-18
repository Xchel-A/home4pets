package mx.edu.uteq.home4pets.validation;

import mx.edu.uteq.home4pets.annotation.ColorAccepted;
import mx.edu.uteq.home4pets.entity.Color;
import mx.edu.uteq.home4pets.service.ColorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ColorAcceptedValidator implements ConstraintValidator<ColorAccepted, Color> {

    @Autowired
    private ColorServiceImpl colorService;

    @Override
    public boolean isValid(Color value, ConstraintValidatorContext context) {
        boolean flag = false;

        if (value.getId() != 0) {
            Optional<Color> item = colorService.findColorById(value.getId());

            if (item.isPresent()) {
                flag = true;
            }
        }

        return flag;
    }
}