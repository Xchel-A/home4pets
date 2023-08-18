package mx.edu.uteq.home4pets.model.request.adoption;

import mx.edu.uteq.home4pets.annotation.ValueOfEnumAccepted;

import javax.validation.constraints.NotEmpty;
import mx.edu.uteq.home4pets.enums.StateAdoptionApplication;

import javax.validation.constraints.NotNull;

public class AdoptionUpdateDto {

    @NotNull(message = "Debe indicar un valor para este campo")
    private Long id;

    @ValueOfEnumAccepted(enumClass = StateAdoptionApplication.class, message = "Este valor no es aceptado para el estado de la adopción")
    @NotEmpty(message = "Debe de indicar un valor")
    private String state;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
