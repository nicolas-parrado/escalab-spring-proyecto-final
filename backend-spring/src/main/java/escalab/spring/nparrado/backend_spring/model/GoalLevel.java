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
@Table(name = "goal_level")
@Data
public class GoalLevel extends RepresentationModel<GoalLevel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGoalLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goalLevel", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("goalLevel")
    private Set<Goal> goals = new HashSet<>();

    @Column(name = "name")
    private String name;

}
