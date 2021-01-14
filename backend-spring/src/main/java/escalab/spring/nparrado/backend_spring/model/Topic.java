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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic", fetch = FetchType.LAZY)
    @Schema(description = "Listado de pensamientos que tiene este tópico asignado")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"topic"})
    private Set<Thought> thoughts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic", fetch = FetchType.LAZY)
    @Schema(description = "Listado de somedays que tiene asignado este tópico")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","topic"})
    private Set<Someday> somedays = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic", fetch = FetchType.LAZY)
    @Schema(description = "Listado de proyectos que tiene asignado este tópico")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","actions","references","topic","goal"})
    private Set<Project> projects = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic", fetch = FetchType.LAZY)
    @Schema(description = "Listado de acciones que tiene asignado este tópico")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"thought","topic","actionStatus","context","delegate","attachments","project"})
    private Set<Action> actions = new HashSet<>();

    @Column(name = "name", length = 70)
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    @Schema(description = "Nombre del Tópico")
    private String name;

    @Column(name = "description", length = 255)
    @Size(max = 255, message = "Description debe tener como máximo 255 caracteres")
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
