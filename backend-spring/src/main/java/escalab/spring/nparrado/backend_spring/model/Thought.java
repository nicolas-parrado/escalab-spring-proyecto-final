package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "thought")
@Schema(description = "Pensamiento que genera una tarea o proyecto")
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
    @Schema(description = "Tema o Tópico al que pertenece este pensamiento")
    @JsonIgnore
    private Topic topic;

    @Lob
    @Type(type = "text")
    @Column(name = "notes")
    @Schema(description = "Notas adicionales en formato Markdown del pensamiento")
    private String notes;

    @Column(name = "processed")
    @Schema(description = "Indica si el pensamiento fue procesado (true) o no (false)")
    private Boolean processed;

    @Column(name = "created_date")
    @Schema(description = "Fecha de creación del pensamiento")
    private LocalDateTime createdDate;
}
