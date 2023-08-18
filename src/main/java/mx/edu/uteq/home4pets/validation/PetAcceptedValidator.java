package mx.edu.uteq.home4pets.validation;

import mx.edu.uteq.home4pets.annotation.PetAccepted;
import mx.edu.uteq.home4pets.entity.Pet;
import mx.edu.uteq.home4pets.service.PetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class PetAcceptedValidator implements ConstraintValidator<PetAccepted, Long> {

    @Autowired
    private PetServiceImpl petService;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        boolean flag = false;

        if ( value != 0) {

            Optional<Pet> pet = petService.findPetById(value);

            if (pet.isPresent()) {
                flag = true;
            }
        }

        return flag;
    }
}
