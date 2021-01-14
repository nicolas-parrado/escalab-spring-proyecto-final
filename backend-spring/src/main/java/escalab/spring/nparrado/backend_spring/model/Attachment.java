package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "attachment")
@Schema(description = "Archivos adjuntos de las tareas")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idAttachment"
)
public class Attachment extends RepresentationModel<Attachment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAttachment;

    @ManyToOne
    @JoinColumn(name = "id_action")
    @Schema(description = "Acción que es dueña de este archivo adjunto")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","topic","actionStatus","context","delegate","attachments","project"})
    private Action action;

    @ManyToOne
    @JoinColumn(name = "id_reference")
    @Schema(description = "Referencia que es dueña de este archivo adjunto")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","attachments","project"})
    private Reference reference;

    @Column(name = "name", length = 70)
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    private String name;

    @Column(name = "created_date")
    @Schema(description = "Fecha de creación")
    private LocalDateTime createdDate;

    @Column(name = "path", length = 1024)
    @Size(min = 1, max = 1024, message = "Path debe tener mínimo 1 carácter y máximo de 1024")
    @Schema(description = "Ruta de acceso al archivo")
    private String path;

    @Column(name = "size")
    @Schema(description = "Tamaño del archivo adjunto")
    private long size;
}
