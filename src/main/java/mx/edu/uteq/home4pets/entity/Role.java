package mx.edu.uteq.home4pets.entity;

import lombok.Data;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserAdoptame> users;

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

    public Set<UserAdoptame> getUsers() {
        return users;
    }

    public void setUsers(Set<UserAdoptame> users) {
        this.users = users;
    }


    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + ", users=" + users + "]";
    }


}
