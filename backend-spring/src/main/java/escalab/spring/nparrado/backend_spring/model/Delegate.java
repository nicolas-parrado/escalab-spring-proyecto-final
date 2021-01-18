package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

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

    @Column(name = "name", length = 70)
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    @Schema(description = "Nombre de la persona delegada")
    private String name;

    @Column(name = "email")
    @Schema(description = "Correo electrónico de la persona delegada")
    @Email
    private String email;

    @Column(name = "active")
    @Schema(description = "Indica si la persona delegada se encuentra activa (true) o no (false)")
    private Boolean active;
}
