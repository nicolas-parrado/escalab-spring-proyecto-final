package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "action_status")
@Schema(description = "Estado de las Acciones")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idActionStatus"
)
public class ActionStatus extends RepresentationModel<ActionStatus> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idActionStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actionStatus", fetch = FetchType.LAZY)
    @Schema(description = "Lista de acciones que tienen este estado asignado")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","topic","actionStatus","context","delegate","attachments","project"})
    private Set<Action> actions = new HashSet<>();

    @Column(name = "name", length = 70)
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    @Schema(description = "Nombre del estado")
    private String name;
}
