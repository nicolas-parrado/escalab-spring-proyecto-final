package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "someday")
@Data
public class Someday extends RepresentationModel<Someday> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSomeday;

    @ManyToOne
    @JoinColumn(name = "id_thought")
    private Thought thought;

    @ManyToOne
    @JoinColumn(name = "id_topic")
    private Topic topic;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "tickle_date")
    private LocalDateTime tickleDate;

    @Lob
    @Column(name = "notes")
    private String notes;
}
