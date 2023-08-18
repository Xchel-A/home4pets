package mx.edu.uteq.home4pets.validation;

import mx.edu.uteq.home4pets.annotation.SizeAccepted;
import mx.edu.uteq.home4pets.entity.Size;
import mx.edu.uteq.home4pets.service.SizeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class SizeAcceptedValidator implements ConstraintValidator<SizeAccepted, Size> {

    @Autowired
    private SizeServiceImpl sizeService;

    @Override
    public boolean isValid(Size value, ConstraintValidatorContext context) {
        boolean flag = false;

        if (value.getId() != 0) {
            Optional<Size> item = sizeService.findSizeById(value.getId());

            if (item.isPresent()) {
                flag = true;
            }
        }

        return flag;
    }
}
