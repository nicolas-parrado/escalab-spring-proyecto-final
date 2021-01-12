package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idProject"
)
public class Project extends RepresentationModel<Project> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProject;

    @ManyToOne
    @JoinColumn(name = "id_thought")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"topic"})
    private Thought thought;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","topic","actionStatus","context","delegate","attachments","project"})
    private Set<Action> actions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","attachments","project"})
    private Set<Reference> references = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_topic")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thoughts","somedays","projects","actions"})
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "id_goal")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"goalLevel","projects"})
    private Goal goal;

    @Column(name = "name")
    private String name;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "done")
    private Boolean done;

    @Column(name = "done_date")
    private LocalDateTime doneDate;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "vision")
    private String vision;

    @Column(name = "brainstorming")
    private String brainstorming;

    @Column(name = "organizing")
    private String organizing;

    @Lob
    @Column(name = "notes")
    private String notes;
}
