package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "goal_level")
@Data
public class GoalLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGoalLevel;

    @Column(name = "name")
    private String name;

}
