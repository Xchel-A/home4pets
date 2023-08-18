package mx.edu.uteq.home4pets.entity;

import lombok.Data;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "blog")
@Data
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 70)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_principal", nullable = false)
    private Boolean isPrincipal;

    @Column(name = "image")
    private String image;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAdoptame user;

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isPrincipal=" + isPrincipal +
                ", image='" + image + '\'' +
                ", createdAt=" + createdAt +
                ", user=" + user +
                '}';
    }
}
