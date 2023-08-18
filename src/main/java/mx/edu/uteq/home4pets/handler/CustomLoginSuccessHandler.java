package mx.edu.uteq.home4pets.handler;

import mx.edu.uteq.home4pets.entity.SessionControlAccess;
import mx.edu.uteq.home4pets.model.responses.InfoToast;
import mx.edu.uteq.home4pets.repository.SessionControlAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private SessionControlAccessRepository sessionControlAccessRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        SessionFlashMapManager flashMapManager = new SessionFlashMapManager();

        FlashMap flashMap = new FlashMap();

        InfoToast info = new InfoToast();

        info.setTitle( "Inicio Sesión");
        info.setMessage("Hola " + authentication.getName() + ", ha iniciado sesión con éxito");
        info.setTypeToast("success");

        flashMap.put("info", info);

        flashMapManager.saveOutputFlashMap(flashMap, request, response);

        // Registering info in session management
        SessionControlAccess sessionAccess = new SessionControlAccess( authentication.getName(), true , new Date());

        sessionControlAccessRepository.save(sessionAccess);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
