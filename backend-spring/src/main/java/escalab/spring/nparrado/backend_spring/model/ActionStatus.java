package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "action_status")
@Data
public class ActionStatus extends RepresentationModel<ActionStatus> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idActionStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actionStatus")
    private Set<Action> actions;

    @Column(name = "name")
    private String name;
}
