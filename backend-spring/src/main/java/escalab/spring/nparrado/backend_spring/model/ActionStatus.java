package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "action_status")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idActionStatus"
)
public class ActionStatus extends RepresentationModel<ActionStatus> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idActionStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actionStatus", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","topic","actionStatus","context","delegate","attachments","project"})
    private Set<Action> actions = new HashSet<>();

    @Column(name = "name")
    private String name;
}
