package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "goal_level")
@Data
public class GoalLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGoalLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goalLevel")
    private Set<Goal> goals;

    @Column(name = "name")
    private String name;

}