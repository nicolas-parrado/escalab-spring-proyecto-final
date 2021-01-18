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
@Table(name = "topic")
@Data
@Schema(description = "Tema sobre el cual se trata la tarea, proyecto, etc")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idTopic"
)
public class Topic extends RepresentationModel<Topic> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTopic;

    @Column(name = "name", length = 70)
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    @Schema(description = "Nombre del Tópico")
    private String name;

    @Column(name = "description", length = 256)
    @Size(max = 256, message = "Description debe tener como máximo 256 caracteres")
    @Schema(description = "Nombre del Tópico")
    private String description;

    @Column(name = "font_color", length = 6)
    @Size(min = 6, max = 6, message = "fontColor debe tener exactamente 6 caracteres")
    @Schema(description = "Color en formato hexadecimal del nombre del tópico")
    private String fontColor;

    @Column(name = "background_color", length = 6)
    @Size(min = 6, max = 6, message = "backgroundColor debe tener exactamente 6 caracteres")
    @Schema(description = "Color en formato hexadecimal del fondo del nombre del tópico")
    private String backgroundColor;
}
