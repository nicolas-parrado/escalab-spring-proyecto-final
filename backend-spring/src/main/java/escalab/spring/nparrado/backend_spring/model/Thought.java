package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "thought")
@Data
public class Thought {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idThought;

    @ManyToOne
    @JoinColumn(name = "id_topic")
    private Topic topic;

    @Column(name = "notes")
    private String notes;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
