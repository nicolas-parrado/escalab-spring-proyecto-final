package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    @JsonIgnoreProperties("references")
    private Thought thought;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reference", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("reference")
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_project")
    @JsonIgnoreProperties("references")
    private Project project;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "notes")
    private String notes;
}
