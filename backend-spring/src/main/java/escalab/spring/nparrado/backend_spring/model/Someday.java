package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "someday")
@Data
@Schema(description = "Tarea o Proyecto que se revisará algún día en el futuro")
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
    @Schema(description = "Pensamiento del que se generó el someday")
    @JsonIgnore
    private Thought thought;

    @Column(name = "name", length = 70)
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    @Schema(description = "Tema o Tópico al que pertenece este someday")
    private String name;

    @Column(name = "created_date")
    @Schema(description = "Fecha de creación del someday")
    private LocalDateTime createdDate;

    @Column(name = "tickle_date")
    @Schema(description = "Fecha en la que se volverá a revisar este someday")
    private LocalDateTime tickleDate;

    @Lob
    @Type(type = "text")
    @Column(name = "notes")
    @Schema(description = "Notas adicionales en formato Markdown del someday")
    private String notes;
}
