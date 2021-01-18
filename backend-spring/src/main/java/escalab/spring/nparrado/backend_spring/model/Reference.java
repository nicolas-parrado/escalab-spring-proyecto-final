package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reference")
@Data
@Schema(description = "Información o Referencia que se guarda para su revisión posterior")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idReference"
)
public class Reference extends RepresentationModel<Reference> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReference;

    @ManyToOne
    @JoinColumn(name = "id_thought")
    @Schema(description = "Pensamiento del que se generó la referencia")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"topic"})
    private Thought thought;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "Listado de archivos adjuntos que tiene esta referencia")
    @JsonIgnore
    private Set<Attachment> attachments = new HashSet<>();

    @Column(name = "name", length = 70)
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    @Schema(description = "Nombre de la referencia")
    private String name;

    @Column(name = "created_date")
    @Schema(description = "Fecha de creación de la referencia")
    private LocalDateTime createdDate;

    @Column(name = "notes")
    @Schema(description = "Notas adicionales en formato Markdown de la referencia")
    private String notes;
}
