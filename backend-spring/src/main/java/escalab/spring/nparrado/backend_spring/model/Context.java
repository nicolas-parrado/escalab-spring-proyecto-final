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
@Table(name = "context")
@Schema(description = "Contexto en el cual se realizará la tarea")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idContext"
)
public class Context extends RepresentationModel<Context> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContext;

    @Column(name = "name", length = 70)
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    private String name;

    @Column(name = "description", length = 256)
    @Size(max = 256, message = "Descripción debe tener como máximo 256 caracteres")
    private String description;
}
