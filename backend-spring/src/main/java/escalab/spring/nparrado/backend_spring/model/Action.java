package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "action")
@Data
public class Action  extends RepresentationModel<Action> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAction;

    @ManyToOne
    @JoinColumn(name = "id_thought")
    private Thought thought;

    @ManyToOne
    @JoinColumn(name = "id_topic")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "id_action_status")
    private ActionStatus actionStatus;

    @ManyToOne
    @JoinColumn(name = "id_context")
    private Context context;

    @ManyToOne
    @JoinColumn(name = "id_delegate")
    private Delegate delegate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "action")
    private Set<Attachment> attachments;

    @ManyToOne
    @JoinColumn(name = "id_project")
    private Project project;

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

    @Column(name = "success")
    private String success;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Column(name = "delegate_follow_up")
    private LocalDateTime delegateFollowUp;

    @Column(name = "next_action")
    private Boolean nextAction;
}
