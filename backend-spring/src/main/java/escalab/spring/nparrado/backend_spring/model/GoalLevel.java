package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "goal_level")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idGoalLevel"
)
public class GoalLevel extends RepresentationModel<GoalLevel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGoalLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goalLevel", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"goalLevel","projects"})
    private Set<Goal> goals = new HashSet<>();

    @Column(name = "name")
    private String name;

}
