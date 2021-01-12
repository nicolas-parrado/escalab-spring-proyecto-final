package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reference")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idReference"
)
public class Reference extends RepresentationModel<Reference> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReference;

    @ManyToOne
    @JoinColumn(name = "id_thought")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"topic"})
    private Thought thought;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reference", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"action","reference"})
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_project")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","actions","references","topic","goal"})
    private Project project;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "notes")
    private String notes;
}
