package mx.edu.uteq.home4pets.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "color")
@Data
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 70)
    private String name;
}
