package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "goal")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idGoal"
)
public class Goal extends RepresentationModel<Goal> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGoal;

    @ManyToOne
    @JoinColumn(name = "id_goal_level")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"goals"})
    private GoalLevel goalLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goal", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","actions","references","topic","goal"})
    private Set<Project> projects = new HashSet<>();

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "vision")
    private String vision;

    @Column(name = "accountability")
    private String accountability;

    @Column(name = "rewards")
    private String rewards;

    @Column(name = "obstacles")
    private String obstacles;

    @Column(name = "support")
    private String support;

    @Column(name = "brainstorming")
    private String brainstorming;

    @Column(name = "achieved")
    private Boolean achieved;

    @Column(name = "achieved_date_time")
    private LocalDateTime achievedDateTime;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Lob
    @Column(name = "notes")
    private String notes;
}
