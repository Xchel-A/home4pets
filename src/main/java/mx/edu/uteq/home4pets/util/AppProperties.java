package mx.edu.uteq.home4pets.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
/*
 * esta clase AppProperties es un componente de Spring que se utiliza para acceder a la propiedad de configuración
 * imageSavePath de la aplicación. El método getImageSavePath() retorna el valor de esta propiedad. La inyección de
 * dependencia se realiza a través del objeto Environment que se autowirea en la clase.*/
@Component
public class AppProperties {
    @Autowired
    private Environment env;

    public String getImageSavePath() {
        return env.getProperty("imageSavePath");
    }
}
