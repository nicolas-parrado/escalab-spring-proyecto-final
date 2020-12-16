package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "action")
@Data
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAction;

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
