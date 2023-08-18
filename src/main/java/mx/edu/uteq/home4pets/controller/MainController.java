package mx.edu.uteq.home4pets.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import mx.edu.uteq.home4pets.entity.Blog;
import mx.edu.uteq.home4pets.entity.UserAdoptame;
import mx.edu.uteq.home4pets.repository.UserAdoptameRepository;
import mx.edu.uteq.home4pets.service.BlogServiceImpl;
import mx.edu.uteq.home4pets.service.UserAdoptameService;
import mx.edu.uteq.home4pets.service.UserAdoptameServiceImpl;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    private final BlogServiceImpl blogService;
    private final UserAdoptameRepository userAdoptame;

    public MainController(BlogServiceImpl blogService, UserAdoptameRepository userAdoptame){
        this.blogService = blogService;
        this.userAdoptame = userAdoptame;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        List<Blog> blogs = blogService.findAllByIsPrincipal(true);
        boolean flagRegister = (blogs.size() > 0) ?  true : false;

        model.addAttribute("listBlogs",blogs);
        model.addAttribute("flagRegister",flagRegister);
        return "index";
    }

    @GetMapping("/manageUsers")
    public String manageUsers(Model model) {
        List<UserAdoptame> users = userAdoptame.findAll();

        model.addAttribute("users", users);

        return "views/manageUsers/manageUsers";
    }

    // Método para leer el texto de un documento PDF (solo como ejemplo)
    private String readPDFText(byte[] pdfBytes) {
        try (PDDocument document = PDDocument.load(pdfBytes)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    //Función para actualizar el status de un usuario, recibe el id y el status
    @PostMapping({"/user/updateStatus"})
    public String updateStatus(Model model, @RequestParam("id") Long id, @RequestParam("status") String status) {
        Optional<UserAdoptame> optionalUser = userAdoptame.findById(id);

        if (optionalUser.isPresent()) {
            UserAdoptame user = optionalUser.get();
            user.setStatus(status);
            userAdoptame.save(user);
        }
        return "redirect:/manageUsers";
    }
}

