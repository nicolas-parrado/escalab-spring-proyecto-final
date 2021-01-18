package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "action")
@Schema(description = "Acciones o tareas a realizar")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idAction"
)
public class Action  extends RepresentationModel<Action> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAction;

    @ManyToOne
    @Schema(description = "Estado de la acción, puede ser pendiente o completado")
    @JoinColumn(name = "id_action_status")
    @JsonIgnore
    private ActionStatus actionStatus;

    @ManyToOne
    @Schema(description = "Contexto asociado a esta acción")
    @JoinColumn(name = "id_context")
    @JsonIgnore
    private Context context;

    @ManyToOne
    @Schema(description = "Persona a quien se delegó la acción")
    @JoinColumn(name = "id_delegate")
    @JsonIgnore
    private Delegate delegate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Attachment> attachments = new HashSet<>();

    @Column(name = "name", length = 70)
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    @Schema(description = "Nombre o descripción corta de la acción a realizar")
    private String name;

    @Column(name = "due_date")
    @Schema(description = "Fecha de vencimiento de la acción")
    private LocalDateTime dueDate;

    @Column(name = "created_date")
    @Schema(description = "Fecha de creación de la acción")
    private LocalDateTime createdDate;

    @Column(name = "done")
    @Schema(description = "Indica si la acción está completada o no")
    private Boolean done;

    @Column(name = "done_date")
    @Schema(description = "Fecha en la cual se completó la acción")
    private LocalDateTime doneDate;

    @Column(name = "success")
    @Size(max = 70, message = "Success no puede superar los 70 caracteres")
    @Schema(description = "Se indica qué se considerará como acción completada")
    private String success;

    @Lob
    @Type(type = "text")
    @Column(name = "notes")
    @Schema(description = "Notas adicionales en formato Markdown de la acción")
    private String notes;

    @Column(name = "delegate_follow_up")
    @Schema(description = "En caso de que la tarea haya sido delegada, se indica la fecha en la cual se debe consultar por ella")
    private LocalDateTime delegateFollowUp;

    @Column(name = "next_action")
    @Schema(description = "Indica si la acción es la próxima acción a realizar")
    private Boolean nextAction;
}
