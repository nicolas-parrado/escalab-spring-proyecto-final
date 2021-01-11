package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
@Data
public class Project extends RepresentationModel<Project> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProject;

    @ManyToOne
    @JoinColumn(name = "id_thought")
    @JsonIgnoreProperties("projects")
    private Thought thought;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("project")
    private Set<Action> actions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("project")
    private Set<Reference> references = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_topic")
    @JsonIgnoreProperties("projects")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "id_goal")
    @JsonIgnoreProperties("projects")
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
