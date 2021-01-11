package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "action_status")
@Data
public class ActionStatus extends RepresentationModel<ActionStatus> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idActionStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actionStatus", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("actionStatus")
    private Set<Action> actions = new HashSet<>();

    @Column(name = "name")
    private String name;
}
