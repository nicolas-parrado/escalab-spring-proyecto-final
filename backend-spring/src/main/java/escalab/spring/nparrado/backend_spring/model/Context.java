package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "context")
@Data
public class Context extends RepresentationModel<Context> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContext;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "context", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("context")
    private Set<Action> actions = new HashSet<>();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
