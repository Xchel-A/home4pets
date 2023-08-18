package mx.edu.uteq.home4pets.controller;


import mx.edu.uteq.home4pets.entity.UserAdoptame;
import mx.edu.uteq.home4pets.model.request.user.UserInsertDto;
import mx.edu.uteq.home4pets.model.responses.InfoToast;
import mx.edu.uteq.home4pets.service.StorageServiceImpl;
import mx.edu.uteq.home4pets.service.UserAdoptameServiceImpl;
import mx.edu.uteq.home4pets.util.ImageManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    private final ImageManager imageManager;
    private final UserAdoptameServiceImpl userAdoptameService;

    private final StorageServiceImpl storageService;
    private final String MESSAGE_FILE_NOT_SELECTED = "Este campo es requerido";

    public LoginController(
            ImageManager imageManager, UserAdoptameServiceImpl userAdoptameService, StorageServiceImpl storageService) {
        this.imageManager = imageManager;
        this.userAdoptameService = userAdoptameService;
        this.storageService = storageService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
                        RedirectAttributes flash) {

        InfoToast info = new InfoToast();
        model.addAttribute("login", true);
        model.addAttribute("user", new UserInsertDto());
        if (principal != null) {
            info.setTitle("Sesión iniciada");
            info.setMessage("Ya cuenta con una sesión activa");
            info.setTypeToast("info");
            flash.addFlashAttribute("info", info);
            return "redirect:/";
        }

        if (error != null) {
            info.setTitle("Error de acceso");
            info.setMessage("Los datos ingresados son erróneos");
            info.setTypeToast("error");
            model.addAttribute("info", info);

            return "views/auth/login";
        }

        if (logout != null) {
            return "redirect:/";
        }

        model.addAttribute("user", new UserInsertDto());


        return "views/auth/login";
    }

    @PostMapping("/create-acount")
    public String createAcount(@RequestParam("ineImg") MultipartFile ineImg,
                               @RequestParam("comprobanteImg") MultipartFile comprobanteImg,
                               Model model, @ModelAttribute UserInsertDto userDto, RedirectAttributes flash) throws IOException {
        model.addAttribute("login", true);

        System.out.println("INE: " + ineImg.getOriginalFilename());
        System.out.println("INE Vacio " + ineImg.isEmpty());
        System.out.println("COMPROBANTE: " + comprobanteImg.getOriginalFilename());
        System.out.println("COMPROBANTE Vacio " + comprobanteImg.isEmpty());

        InfoToast info = new InfoToast();
        Map<String, List<String>> validationsAcount = userAdoptameService.getValidationInsertUserAdoptame(userDto);

        String passwordFine = userDto.getPassword().substring(0,userDto.getPassword().indexOf(","));

        userDto.setPassword(passwordFine);

        if (!validationsAcount.isEmpty()) {
            info.setTitle("Error al crear la cuenta");
            info.setMessage("Los datos ingresados son erróneos");
            info.setTypeToast("error");
            model.addAttribute("info", info);
            model.addAttribute("errors", validationsAcount);
            model.addAttribute("user", userDto);
            return "views/auth/login";
        }

        UserAdoptame userExist = userAdoptameService.findUserByUsername(userDto.getUsername());
        if (userExist != null) {
            info.setTitle("Error al crear la cuenta");
            info.setMessage("El username ya existe");
            info.setTypeToast("error");
            model.addAttribute("info", info);
            model.addAttribute("user", userDto);
            return "views/auth/login";
        }

        if (!ineImg.isEmpty() && !comprobanteImg.isEmpty()) {
            String ineImgName = imageManager.insertImage(ineImg);
            System.out.println("INE_IMG_NAME -> : " + ineImgName);
            String comprobanteImgName = imageManager.insertImage(comprobanteImg);
            System.out.println("COMPROBANTE_IMG_NAME -> : " + comprobanteImgName);
            boolean transferencia = storageService.almacenarApache(ineImg,comprobanteImg,ineImgName,comprobanteImgName);
            if ((ineImgName == null || comprobanteImgName == null)||!transferencia) {
                info.setTitle("Error de imagen al guardar");
                info.setTypeToast("error");
                info.setMessage("Sucedio un error al intentar guardar la imagen");

                model.addAttribute("IneError", MESSAGE_FILE_NOT_SELECTED);
                model.addAttribute("ComprobanteError", MESSAGE_FILE_NOT_SELECTED);
                return "views/auth/login";
            }

            userDto.setStatus("Pendiente");
            userDto.setIne(ineImgName);
            userDto.setComprobante(comprobanteImgName);
            boolean userWasInsert = userAdoptameService.saveUser(userDto);

            if (userWasInsert) {
                info.setTitle("Solicitud de cuenta creada");
                info.setMessage("Aguarda un momento, estamos verificando que los datos de tu cuenta sean verdaderos ".concat(userDto.getUsername()));
                info.setTypeToast("success");
                model.addAttribute("info", info);
                UserInsertDto newUserDto = new UserInsertDto();
                model.addAttribute("user", newUserDto);
                return "views/auth/login";
            } else if(!userWasInsert){
                info.setTitle("Error al crear la cuenta");
                info.setMessage("Los datos ingresados son erróneos");
                info.setTypeToast("error");
                model.addAttribute("info", info);
                model.addAttribute("user", userDto);
                return "views/auth/login";
            }
        }
        return "views/auth/login";
    }



}
