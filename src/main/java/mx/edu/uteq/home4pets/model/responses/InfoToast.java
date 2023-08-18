package mx.edu.uteq.home4pets.model.responses;

import lombok.Data;

@Data
public class InfoToast {
    private String title;
    private String message;
    private String typeToast;
}
