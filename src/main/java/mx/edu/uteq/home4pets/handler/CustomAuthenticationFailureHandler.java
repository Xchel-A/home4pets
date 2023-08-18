package mx.edu.uteq.home4pets.handler;

import mx.edu.uteq.home4pets.model.responses.InfoToast;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception .getMessage().equals("El usuario no esta habilitado")) {
            FlashMapManager flashMapManager = new SessionFlashMapManager();

            FlashMap flashMap = new FlashMap();

            InfoToast info = new InfoToast();

            info.setTitle("Estatus Pendiente");
            info.setMessage("Seguimos validando el estatus de tu cuenta");
            info.setTypeToast("success");

            flashMap.put("info", info);

            flashMapManager.saveOutputFlashMap(flashMap, request, response);

            response.sendRedirect("/login");
        }
        else if(exception.getMessage().equals("Bad credentials")){
            FlashMapManager flashMapManager = new SessionFlashMapManager();

            FlashMap flashMap = new FlashMap();
            InfoToast info = new InfoToast();
            info.setTitle("Error de acceso");
            info.setMessage("Los datos ingresados son erróneos");
            info.setTypeToast("error");
            flashMap.put("info", info);
            flashMapManager.saveOutputFlashMap(flashMap, request, response);
            response.sendRedirect("/login");
        }
    }
}
