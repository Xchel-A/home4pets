package mx.edu.uteq.home4pets.service;

import mx.edu.uteq.home4pets.entity.UserAdoptame;
import mx.edu.uteq.home4pets.model.request.user.UserInsertDto;

import java.io.IOException;
import java.util.Optional;


public interface UserAdoptameService {
    boolean saveUser(UserInsertDto user) throws IOException;

    Optional<UserAdoptame> findUserById(Long id);

    UserAdoptame findUserByUsername(String username);
}
