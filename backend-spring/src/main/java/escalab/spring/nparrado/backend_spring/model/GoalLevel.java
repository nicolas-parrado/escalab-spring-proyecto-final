package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "goal_level")
@Data
@Schema(description = "Nivel de los objetivos a realizar")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idGoalLevel"
)
public class GoalLevel extends RepresentationModel<GoalLevel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGoalLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goalLevel", fetch = FetchType.LAZY)
    @Schema(description = "Listado de objetivos con este nivel")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"goalLevel","projects"})
    private Set<Goal> goals = new HashSet<>();

    @Column(name = "name", length = 70)
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    @Schema(description = "Nombre del nivel de objetivo")
    private String name;

}
