package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "thought")
@Data
public class Thought extends RepresentationModel<Thought> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idThought;

    @ManyToOne
    @JoinColumn(name = "id_topic")
    @JsonIgnore
    private Topic topic;

    @Column(name = "notes")
    private String notes;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
