package mx.edu.uteq.home4pets.service;

import mx.edu.uteq.home4pets.entity.Role;
import mx.edu.uteq.home4pets.entity.UserAdoptame;
import mx.edu.uteq.home4pets.model.request.user.UserInsertDto;
import mx.edu.uteq.home4pets.repository.RolRepository;
import mx.edu.uteq.home4pets.repository.UserAdoptameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.util.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;

@Service
public class UserAdoptameServiceImpl implements UserAdoptameService {

    private final Validator validator;

    private final Logger logger = LoggerFactory.getLogger(UserAdoptameServiceImpl.class);
    private final UserAdoptameRepository userAdoptameRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAdoptameServiceImpl(
            UserAdoptameRepository userAdoptameRepository, Validator validator, RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {
        this.userAdoptameRepository = userAdoptameRepository;
        this.validator = validator;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAdoptame findUserByUsername(String username) {

        return userAdoptameRepository.findUserByUsername(username);
    }

    @Override
    public boolean saveUser(UserInsertDto userDto) throws IOException {
        boolean validInsert = false;

        Optional<Role> rol = rolRepository.findRolByName(userDto.getRole().getName());

        if (rol.isPresent()) {
            if (rol.get().getName().equals("ROLE_ADOPTADOR") || rol.get().getName().equals("ROLE_VOLUNTARIO")) {
                UserAdoptame user = new UserAdoptame();

                String passwordEcrypt = passwordEncoder.encode(userDto.getPassword());
                userDto.setPassword(passwordEcrypt);

                BeanUtils.copyProperties(userDto, user);
                user.setEnabled(true);
                user.getRoles().add(rol.get());
                System.out.println("INE_DTO ----> " + userDto.getIne());
                user.setIneImg(userDto.getIne());
                System.out.println("INE_USER ----> " + user.getIneImg());
                System.out.println("COMPROBANTE_DTO ----> " + userDto.getComprobante());
                user.setComprobanteImg(userDto.getComprobante());
                System.out.println("COMPROBANTE_USER ----> " + user.getComprobanteImg());

                try {
                    UserAdoptame userInsertedBd = userAdoptameRepository.save(user);

                    if (userInsertedBd.getId() != 0) {
                        validInsert = true;
                    }
                } catch (Exception e) {
                    logger.error("error to insert userAdoptame");
                }
            }
        }

        return validInsert;
    }

    @Override
    public Optional<UserAdoptame> findUserById(Long id) {
        return userAdoptameRepository.findById(id);
    }

    public Map<String, List<String>> getValidationInsertUserAdoptame(UserInsertDto userDto) {
        Set<ConstraintViolation<UserInsertDto>> violations = validator.validate(userDto);
        Map<String, List<String>> errors = new HashMap<>();

        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserInsertDto> error : violations) {
                List<String> messages = new ArrayList<>();
                Path path = error.getPropertyPath();
                String key = path.toString();
                String message = error.getMessage();
                logger.info("Error " + error.getPropertyPath().toString()+"   " +error.getMessage() );
                if (errors.get(key) != null) {
                    errors.get(key).add(message);
                } else {
                    messages.add(message);
                    errors.put(key, messages);
                }
            }
        }
        return errors;
    }
}