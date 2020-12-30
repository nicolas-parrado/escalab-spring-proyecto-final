package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "reference")
@Data
public class Reference extends RepresentationModel<Reference> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReference;

    @ManyToOne
    @JoinColumn(name = "id_thought")
    private Thought thought;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reference")
    private Set<Attachment> attachments;

    @ManyToOne
    @JoinColumn(name = "id_project")
    private Project project;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "notes")
    private String notes;
}
