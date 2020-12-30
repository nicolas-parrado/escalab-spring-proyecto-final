package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "context")
@Data
public class Context extends RepresentationModel<Context> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContext;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "context")
    private Set<Action> actions;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
