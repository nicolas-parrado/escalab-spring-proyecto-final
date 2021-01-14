package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "delegate")
@Schema(description = "Persona a la cual se le delega una tarea")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idDelegate"
)
public class Delegate extends RepresentationModel<Delegate> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDelegate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "delegate", fetch = FetchType.LAZY)
    @Schema(description = "Listado de acciones que tiene delegada la persona ")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","topic","actionStatus","context","delegate","attachments","project"})
    private Set<Action> actions = new HashSet<>();

    @Column(name = "name", length = 70)
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    private String name;

    @Column(name = "email")
    @Schema(description = "Correo electrónico de la persona delegada")
    @Email
    private String email;

    @Column(name = "active")
    @Schema(description = "Indica si la persona delegada se encuentra activa (true) o no (false)")
    private Boolean active;
}
