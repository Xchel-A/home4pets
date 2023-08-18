package mx.edu.uteq.home4pets.service;

import mx.edu.uteq.home4pets.entity.MovementManagement;
import mx.edu.uteq.home4pets.repository.MovementManagementRepository;
import org.springframework.stereotype.Service;

@Service
public class MovementManagementServiceImpl implements MovementManagementService{

    private final MovementManagementRepository movementManagementRepository;

    public MovementManagementServiceImpl(MovementManagementRepository repository) {
        this.movementManagementRepository = repository;
    }

    @Override
    public void createOrUpdate(MovementManagement movementManagement) {

        movementManagementRepository.save(movementManagement);
    }
}

