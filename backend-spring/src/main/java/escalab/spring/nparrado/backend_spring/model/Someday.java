package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "someday")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idSomeday"
)
public class Someday extends RepresentationModel<Someday> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSomeday;

    @ManyToOne
    @JoinColumn(name = "id_thought")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"topic"})
    private Thought thought;

    @ManyToOne
    @JoinColumn(name = "id_topic")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thoughts","somedays","projects","actions"})
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
