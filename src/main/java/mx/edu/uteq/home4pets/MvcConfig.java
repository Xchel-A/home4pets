package mx.edu.uteq.home4pets;

import mx.edu.uteq.home4pets.handler.CustomAuthenticationFailureHandler;
import mx.edu.uteq.home4pets.handler.CustomLoginSuccessHandler;
import mx.edu.uteq.home4pets.handler.CustomLogoutSuccessHandler;
import mx.edu.uteq.home4pets.util.AppProperties;
import mx.edu.uteq.home4pets.util.GeneralInfoApp;
import mx.edu.uteq.home4pets.util.InfoMovement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.*;


import java.nio.file.Paths;

// La clase MvcConfig implementa la interfaz WebMvcConfigurer, lo que permite personalizar la configuración de Spring MVC.
//  Esta anotación marca la clase como una clase de configuración de Spring.
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final AppProperties appProperties;

    public MvcConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    //Este método define un bean de tipo BCryptPasswordEncoder, que es un algoritmo de hashing utilizado
    // para el cifrado de contraseñas.
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    //Este método define un bean de tipo CustomLoginSuccessHandler, que es un controlador personalizado para el manejo
    // del éxito de inicio de sesión.
    public static CustomLoginSuccessHandler loginSuccessHandler() {return new CustomLoginSuccessHandler();}

    @Bean
    //Este método define un bean de tipo CustomLogoutSuccessHandler, que es un controlador personalizado
    // para el manejo del éxito de cierre de sesión.
    public static CustomLogoutSuccessHandler customLogoutSuccessHandler() { return new CustomLogoutSuccessHandler();}

    @Bean
    public static CustomAuthenticationFailureHandler customAuthenticationFailureHandler(){return  new CustomAuthenticationFailureHandler();}
    @Bean(name = "AppProperties")
    //Este método define un bean de tipo AppProperties, que proporciona acceso a las propiedades de configuración de la aplicación.
    public static AppProperties getAppProperties() {
        return new AppProperties();
    }

    @Bean
    public static InfoMovement getInfoMovement() {
        return new InfoMovement();
    }

    @Bean
    public static GeneralInfoApp getGeneralInfoApp() { return new GeneralInfoApp();}

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error_403").setViewName("error/403");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        String pathImages = appProperties.getImageSavePath() != null ?
                appProperties.getImageSavePath() :
                "";

        String resourcePath = Paths.get(pathImages).toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(resourcePath);
    }
}
