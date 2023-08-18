package mx.edu.uteq.home4pets.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionController {

    private final Logger logger = LoggerFactory.getLogger(AppExceptionController.class);

    @ExceptionHandler(NumberFormatException.class)
    public String numberFormat(NumberFormatException ex) {
        logger.error("error to format number"+ex.getMessage());
        return "error/500";
    }

}
