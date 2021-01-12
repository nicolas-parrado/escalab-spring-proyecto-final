package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "context")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idContext"
)
public class Context extends RepresentationModel<Context> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContext;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "context", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","topic","actionStatus","context","delegate","attachments","project"})
    private Set<Action> actions = new HashSet<>();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
