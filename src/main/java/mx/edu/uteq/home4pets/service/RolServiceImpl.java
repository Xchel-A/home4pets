package mx.edu.uteq.home4pets.service;

import java.util.List;

import mx.edu.uteq.home4pets.entity.Role;
import mx.edu.uteq.home4pets.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RolServiceImpl implements RolService {

    @Autowired
    RolRepository rolRepository;

    @Override
    @Transactional
    public List<Role> findAllRol() {
        return  rolRepository.findAll();
    }
}
