package mx.edu.uteq.home4pets.repository;

import mx.edu.uteq.home4pets.entity.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PetRepository extends PagingAndSortingRepository<Pet, Long>{

    Page<Pet> findAllByAvailableAdoptionAndTypeAndIsAccepted(Boolean available, String type, String isAccepted, Pageable pageable);

    @Query("SELECT p FROM Pet p WHERE p.type LIKE ?1 AND p.isAccepted LIKE 'aceptado' AND p.availableAdoption = true AND (p.color.id = ?2 OR p.size.id = ?3 OR p.personality.id = ?4)")
    Page<Pet> findPetsByColorSizeOrPersonalityToAdopt(String typePet, Long colorId, Long sizeId, Long personalityId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Pet p SET p.availableAdoption = ?2 WHERE p.id = ?1")
    int changeAvailableAdoptionPet(Long id, Boolean availableAdoption);

    //Metodo para eliminar una mascota
    @Transactional
    @Modifying
    @Query("DELETE FROM Pet p WHERE p.id = ?1")
    int deletePetById(Long id);

    //Metodo para obtener todas las mascotas de un usuario por su id
    /*@Query("SELECT p FROM Pet p WHERE p.user = ?1")
    Page<Pet> findAllByUser(int id, Pageable pageable);*/
}
