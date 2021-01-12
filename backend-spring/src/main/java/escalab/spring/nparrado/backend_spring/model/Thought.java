package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "thought")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idThought"
)
public class Thought extends RepresentationModel<Thought> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idThought;

    @ManyToOne
    @JoinColumn(name = "id_topic")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thoughts","somedays","projects","actions"})
    private Topic topic;

    @Column(name = "notes")
    private String notes;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
