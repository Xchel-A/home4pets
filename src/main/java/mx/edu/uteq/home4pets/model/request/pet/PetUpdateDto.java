package mx.edu.uteq.home4pets.model.request.pet;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import mx.edu.uteq.home4pets.annotation.ColorAccepted;
import mx.edu.uteq.home4pets.annotation.PersonalityAccepted;
import mx.edu.uteq.home4pets.annotation.SizeAccepted;
import mx.edu.uteq.home4pets.annotation.ValueOfEnumAccepted;
import mx.edu.uteq.home4pets.entity.Color;
import mx.edu.uteq.home4pets.entity.Personality;
import mx.edu.uteq.home4pets.entity.Size;
import mx.edu.uteq.home4pets.enums.Sex;
import mx.edu.uteq.home4pets.enums.TypePet;

public class PetUpdateDto {

    @NotNull(message = "Debe de indicar esta propiedad")
    @Positive(message = "Debe de ser positivo")
    private Long id;

    @NotEmpty(message = "Debe de indicar el nombre de la mascota")
    @Pattern(regexp = "[a-zA-ZñÑáéíóúÁÉÍÓÚ ]{3,250}", message = "Valor no aceptado")
    private String name;

    @NotEmpty(message = "Debe indicar la edad de la mascota")
    @Pattern(regexp = "[a-zA-Z0-9ñÑ ]{1,40}", message = "Valor no aceptado")
    private String age;

    @ValueOfEnumAccepted(enumClass = Sex.class, message = "Este valor no es aceptado para el sexo")
    private String sex;

    @ValueOfEnumAccepted(enumClass = TypePet.class, message = "Este tipo de mascota no es aceptado")
    private String type;

    @NotEmpty(message = "El nombre de la imagen no debe estar vacio")
    private String image;

    @SizeAccepted(message = "Este tamaño no es aceptado")
    @NotNull
    private Size size;

    @ColorAccepted(message = "Este color no es aceptado")
    @NotNull
    private Color color;

    @PersonalityAccepted(message = "Este caracter no es aceptado")
    @NotNull
    private Personality personality;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Personality getPersonality() {
        return personality;
    }

    public void setPersonality(Personality personality) {
        this.personality = personality;
    }
}

