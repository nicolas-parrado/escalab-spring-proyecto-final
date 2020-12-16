package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProject;

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
