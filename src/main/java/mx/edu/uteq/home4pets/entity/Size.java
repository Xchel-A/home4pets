package mx.edu.uteq.home4pets.entity;

import javax.persistence.*;
import lombok.Data;


@Entity
@Table(name = "size")
@Data
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

}
