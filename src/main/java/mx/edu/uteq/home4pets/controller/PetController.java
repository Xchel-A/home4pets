package mx.edu.uteq.home4pets.controller;

import mx.edu.uteq.home4pets.entity.Color;
import mx.edu.uteq.home4pets.entity.Personality;
import mx.edu.uteq.home4pets.entity.Pet;
import mx.edu.uteq.home4pets.entity.Size;
import mx.edu.uteq.home4pets.enums.TracingRegisterPet;
import mx.edu.uteq.home4pets.model.request.pet.PetInsertDto;
import mx.edu.uteq.home4pets.model.request.pet.PetSearchDto;
import mx.edu.uteq.home4pets.model.request.pet.PetTracingRegisterDto;
import mx.edu.uteq.home4pets.model.request.pet.PetUpdateDto;
import mx.edu.uteq.home4pets.model.responses.InfoToast;
import mx.edu.uteq.home4pets.service.ColorServiceImpl;
import mx.edu.uteq.home4pets.service.PersonalityServiceImpl;
import mx.edu.uteq.home4pets.service.PetServiceImpl;
import mx.edu.uteq.home4pets.service.SizeServiceImpl;
import mx.edu.uteq.home4pets.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path = "/pets")
public class PetController {

    private final PetServiceImpl petService;

    private final ColorServiceImpl colorService;

    private final PersonalityServiceImpl personalityService;

    private final SizeServiceImpl sizeService;

    private final ImageManager imageManager;

    private final InfoMovement infoMovement;

    private final GeneralInfoApp generalInfoApp;

    private List<Color> listColors = new ArrayList<>();

    private List<Personality> listPersonalities = new ArrayList<>();

    private List<Size> listSizes = new ArrayList<>();

    private final String LIST_COLORS_NAME = "listColors";

    private final String LIST_PERSONALITIES_NAME = "listPersonalities";

    private final String LIST_SIZES_NAME = "listSizes";

    private final String MODULE_NAME = "PETS";

    private final String MESSAGE_FILE_NOT_SELECTED = "Debe de seleccionar una imagen";

    public PetController(PetServiceImpl petService,
                         ColorServiceImpl colorService,
                         PersonalityServiceImpl personalityService,
                         SizeServiceImpl sizeService,
                         ImageManager imageManager,
                         InfoMovement infoMovement,
                         GeneralInfoApp generalInfoApp) {

        this.petService = petService;
        this.colorService = colorService;
        this.personalityService = personalityService;
        this.sizeService = sizeService;
        this.imageManager = imageManager;
        this.infoMovement = infoMovement;
        this.generalInfoApp = generalInfoApp;
        this.definePetsInfoLists();
    }

    @GetMapping({ "/adopt/{type}", "/adopt" })
    public String findPetToAdopt(@PathVariable(name = "type") String type,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 Model model) {
        this.definePetsInfoLists();
        generalInfoApp.setTypePet(type);

        int itemsByPage = 6;
        Pageable pageRequest = PageRequest.of(page, itemsByPage, Sort.by("createdAt").descending());

        String valueTracingToSearch = TracingRegisterPet.aceptado.toString();

        Page<Pet> pets = petService.findPetsToAdopt(type, valueTracingToSearch, pageRequest);

        PageRender<Pet> pageRender = new PageRender<>("/pets/adopt/".concat(type), pets);

        PetSearchDto petSearchDto = new PetSearchDto();
        petSearchDto.setTypePet(type);

        model.addAttribute("listPets", pets);
        model.addAttribute("page", pageRender);
        model.addAttribute(LIST_COLORS_NAME, this.listColors);
        model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
        model.addAttribute(LIST_SIZES_NAME, this.listSizes);
        model.addAttribute("search", petSearchDto);
        model.addAttribute("typePet", generalInfoApp.getTypePet());

        return "views/pet/lista";
    }

    @GetMapping("/filter")
    public String filterPets(PetSearchDto pet,
                             @RequestParam(name = "page", defaultValue = "0") int page,
                             Model model) {

        int itemsByPageSearch = 6;

        Pageable pageRequest = PageRequest.of(page, itemsByPageSearch, Sort.by("createdAt").descending());

        if (pet.getTypePet() == null) {
            pet.setTypePet(generalInfoApp.getTypePet());
        }

        Page<Pet> pets = petService.findPetsByColorSizeOrPersonality(pet, pageRequest);

        String pathToSerch = "?typePet=" + pet.getTypePet() + "&colorId=" + pet.getColorId() + "&sizeId="
                + pet.getSizeId() + "&personalityId=" + pet.getPersonalityId();

        PageRender<Pet> pageRender = new PageRender<>("/pets/filter".concat(pathToSerch), pets);

        model.addAttribute(LIST_COLORS_NAME, this.listColors);
        model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
        model.addAttribute(LIST_SIZES_NAME, this.listSizes);
        model.addAttribute("listPets", pets);
        model.addAttribute("page", pageRender);
        model.addAttribute("typePet", generalInfoApp.getTypePet());
        model.addAttribute("search", pet);

        return "views/pet/lista";
    }

    @GetMapping("/management_list")
    @Secured({ "ROLE_ADMINISTRADOR", "ROLE_VOLUNTARIO" })
    public String findListPetsManagement(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        int itemsByPage = 5;
        Pageable pageRequest = PageRequest.of(page, itemsByPage, Sort.by("createdAt").descending());

        Page<Pet> pets = petService.findAll(pageRequest);

        PageRender<Pet> pageRender = new PageRender<>("/pets/management_list", pets);

        model.addAttribute("listPets", pets);
        model.addAttribute("page", pageRender);
        model.addAttribute("index", (itemsByPage * page));
        return "views/pet/listTablePets";
    }

    @GetMapping("/create")
    @Secured({ "ROLE_VOLUNTARIO" })
    public String createPet(Model model) {
        this.definePetsInfoLists();
        model.addAttribute("pet", new PetInsertDto());
        model.addAttribute(LIST_COLORS_NAME, this.listColors);
        model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
        model.addAttribute(LIST_SIZES_NAME, this.listSizes);

        return "views/pet/addPet";
    }

    @PostMapping("/save")
    @Secured({ "ROLE_VOLUNTARIO" })
    public String save(@ModelAttribute("pet") PetInsertDto pet,
                       Authentication auth,
                       Model model,
                       @RequestParam("imageFile") MultipartFile imageFile,
                       RedirectAttributes flash) throws IOException {

        InfoToast info = new InfoToast();

        Map<String, List<String>> validation = petService.getValidationToInsertPet(pet);

        infoMovement.setActionMovement("INSERT");
        infoMovement.setUsername(auth.getName());
        infoMovement.setModuleName(MODULE_NAME);

        if (!validation.isEmpty() || imageFile.isEmpty()) {
            model.addAttribute(LIST_COLORS_NAME, this.listColors);
            model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
            model.addAttribute(LIST_SIZES_NAME, this.listSizes);
            model.addAttribute("errors", validation);
            model.addAttribute("fileError", MESSAGE_FILE_NOT_SELECTED);
            return "views/pet/addPet";
        }

        if (!imageFile.isEmpty()) {
            String imageName = imageManager.insertImage(imageFile);

            if (imageName == null) {

                info.setTitle("Error de imagen al guardar");
                info.setTypeToast("error");
                info.setMessage("Sucedio un error al intentar guardar la imagen");

                model.addAttribute(LIST_COLORS_NAME, this.listColors);
                model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
                model.addAttribute(LIST_SIZES_NAME, this.listSizes);

                model.addAttribute("fileError", MESSAGE_FILE_NOT_SELECTED);
                model.addAttribute("info", info);
                return "views/pet/addPet";
            }

            boolean petWasInserted = petService.create(pet, imageName);

            if (petWasInserted) {
                info.setTitle("Mascota registrada");
                info.setMessage("Se registro a ".concat(pet.getName()).concat(" correctamente"));
                info.setTypeToast("success");

                flash.addFlashAttribute("info", info);
            }
        }

        return "redirect:/pets/management_list";
    }

    @GetMapping("/find_update/{id}")
    @Secured({ "ROLE_VOLUNTARIO" })
    public String findPetToUpdate(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {

        InfoToast info = new InfoToast();

        Optional<Pet> petExisted = petService.findPetById(id);

        if (petExisted.isPresent()) {

            PetUpdateDto petInfo = new PetUpdateDto();

            BeanUtils.copyProperties(petExisted.get(), petInfo);

            this.definePetsInfoLists();
            model.addAttribute("pet", petInfo);
            model.addAttribute(LIST_COLORS_NAME, this.listColors);
            model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
            model.addAttribute(LIST_SIZES_NAME, this.listSizes);

            return "views/pet/updatePet";

        }

        info.setTitle("Mascota no encontrada");
        info.setMessage("La información que busca no es valida");
        info.setTypeToast("error");

        flash.addFlashAttribute("info", info);

        return "redirect:/pets/management_list";
    }

    @PostMapping("/update")
    @Secured({ "ROLE_VOLUNTARIO" })
    public String update(PetUpdateDto pet,
                         Authentication auth,
                         Model model,
                         @RequestParam("imageFile") MultipartFile imageFile,
                         RedirectAttributes flash) throws IOException {
        InfoToast info = new InfoToast();

        infoMovement.setActionMovement("UPDATE");
        infoMovement.setUsername(auth.getName());
        infoMovement.setModuleName(MODULE_NAME);

        Optional<Pet> petExisted = petService.findPetById(pet.getId());

        if (petExisted.isEmpty()) {
            info.setTitle("Mascota no valida");
            info.setMessage("La información que busca no es valida");
            info.setTypeToast("error");

            flash.addFlashAttribute("info", info);
            return "redirect:/pets/management_list";
        }

        Map<String, List<String>> validationsUpdate = petService.getValidationToUpdatePet(pet);

        if (!validationsUpdate.isEmpty()) {
            model.addAttribute("errors", validationsUpdate);
            model.addAttribute(LIST_COLORS_NAME, this.listColors);
            model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
            model.addAttribute(LIST_SIZES_NAME, this.listSizes);
            model.addAttribute("pet", pet);
            return "views/pet/updatePet";
        }

        if (!imageFile.isEmpty()) {
            String imageName = imageManager.insertImage(imageFile);

            if (imageName == null) {

                info.setTitle("Error de imagen al actualizar");
                info.setMessage("Sucedio un error al intentar guardar la imagen");
                info.setTypeToast("error");

                model.addAttribute(LIST_COLORS_NAME, this.listColors);
                model.addAttribute(LIST_PERSONALITIES_NAME, this.listPersonalities);
                model.addAttribute(LIST_SIZES_NAME, this.listSizes);
                model.addAttribute("fileError", MESSAGE_FILE_NOT_SELECTED);
                model.addAttribute("info", info);
                return "views/pet/updatePet";
            }

            pet.setImage(imageName);
        }

        boolean petWasUpdated = petService.update(pet);

        if (petWasUpdated) {
            info.setTitle("Mascota actualizada");
            info.setMessage("Se actualizo a ".concat(pet.getName()).concat(" correctamente"));
            info.setTypeToast("success");

            flash.addFlashAttribute("info", info);
        }

        return "redirect:/pets/management_list";
    }

    @GetMapping("/detail-admin/{id}")
    @Secured({ "ROLE_ADMINISTRADOR", "ROLE_VOLUNTARIO" })
    public String findPetDetail(@PathVariable("id") Long id, Authentication auth, Model model,
                                RedirectAttributes flash) {

        InfoToast info = new InfoToast();

        Optional<Pet> petExisted = petService.findPetById(id);

        if (petExisted.isPresent()) {

            model.addAttribute("pet", petExisted.get());

            boolean isAdmin = ValidationCredentials.validateCredential(auth.getAuthorities(), "ROLE_ADMINISTRADOR");

            if (isAdmin) {
                PetTracingRegisterDto petDto = new PetTracingRegisterDto();

                BeanUtils.copyProperties(petExisted.get(), petDto);
                model.addAttribute("petTracing", petDto);
                model.addAttribute("tracingOptions", TracingRegisterPet.values());
            }

            return "views/pet/detailPet";

        }

        info.setTitle("Mascota no encontrada");
        info.setMessage("La información que busca no es correcta");
        info.setTypeToast("error");

        flash.addFlashAttribute("info", info);

        return "redirect:/pets/management_list";
    }

    @PostMapping("/change")
    @Secured({ "ROLE_ADMINISTRADOR" })
    public String acceptOrRejectPet(PetTracingRegisterDto pet,
                                    Authentication auth,
                                    Model model,
                                    RedirectAttributes flash) {
        InfoToast info = new InfoToast();

        infoMovement.setActionMovement("UPDATE");
        infoMovement.setUsername(auth.getName());
        infoMovement.setModuleName(MODULE_NAME);

        Optional<Pet> petExisted = petService.findPetById(pet.getId());

        if (petExisted.isEmpty()) {
            info.setTitle("Mascota no valida");
            info.setMessage("La información enviada no es permitida");
            info.setTypeToast("error");

            flash.addFlashAttribute("info", info);
            return "redirect:/pets/management_list";
        }

        Map<String, List<String>> validationsUpdate = petService.getValidationToAcceptOrReject(pet);

        if (!validationsUpdate.isEmpty()) {
            model.addAttribute("pet", petExisted.get());
            model.addAttribute("errors", validationsUpdate);
            model.addAttribute("petTracing", pet);
            model.addAttribute("tracingOptions", TracingRegisterPet.values());
        } else {
            boolean petWasUpdated = petService.acceptOrRejectPet(pet);

            if (petWasUpdated) {
                info.setTitle("Mascota ".concat(petExisted.get().getName()).concat(" actualizada"));
                info.setMessage("Se asigno el valor de ".concat(pet.getIsAccepted()).concat(" correctamente"));
                info.setTypeToast("success");

                flash.addFlashAttribute("info", info);

                return "redirect:/pets/detail-admin/".concat(pet.getId().toString());

            }
        }

        return "views/pet/detailPet";
    }

    private void definePetsInfoLists() {
        Long itemsColors = colorService.countAllColors();
        int listColorsSize = this.listColors.size();

        Long itemsPersonalities = personalityService.countAllPersonalities();
        int listPersonalitiesSize = this.listPersonalities.size();

        Long itemsSizes = sizeService.countAllSizes();
        int listSizesSize = this.listSizes.size();

        if (listColorsSize == 0 || itemsColors > listColorsSize || itemsColors < listColorsSize) {
            this.listColors = colorService.findAllColors();
        }

        if (listPersonalitiesSize == 0 || itemsPersonalities > listPersonalitiesSize
                || itemsPersonalities < listPersonalitiesSize) {
            this.listPersonalities = personalityService.findAllPersonalities();
        }

        if (listSizesSize == 0 || itemsSizes > listSizesSize || itemsSizes < listSizesSize) {
            this.listSizes = sizeService.findAllSizes();
        }
    }

}