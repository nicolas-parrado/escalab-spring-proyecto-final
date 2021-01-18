package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Size;

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

    @Column(name = "name", length = 70)
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    @Schema(description = "Nombre del estado")
    private String name;
}
