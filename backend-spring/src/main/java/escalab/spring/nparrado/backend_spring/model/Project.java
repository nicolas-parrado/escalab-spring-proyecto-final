package escalab.spring.nparrado.backend_spring.model;

import com.fasterxml.jackson.annotation.*;
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
@Table(name = "project")
@Schema(description = "Proyecto que se quiere emprender")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idProject"
)
public class Project extends RepresentationModel<Project> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProject;

    @ManyToOne
    @JoinColumn(name = "id_thought")
    @Schema(description = "Pensamiento del que se generó el proyecto")
    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"topic"})
    private Thought thought;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Listado de acciones a realizar en este proyecto")
    @JsonIgnore
    private Set<Action> actions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "Listado de referencias que tiene este proyecto")
    @JsonIgnore
    private Set<Reference> references = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_topic")
    @Schema(description = "Tema o Tópico al que pertenece este proyecto")
    @JsonIgnore
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "id_goal")
    @Schema(description = "Objetivo al que pertenece este proyecto")
    @JsonIgnore
    private Goal goal;

    @Column(name = "name", length = 70)
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    @Schema(description = "Nombre del proyecto")
    private String name;

    @Column(name = "due_date")
    @Schema(description = "Fecha de término o vencimiento del proyecto")
    private LocalDateTime dueDate;

    @Column(name = "created_date")
    @Schema(description = "Fecha de creación del proyecto")
    private LocalDateTime createdDate;

    @Column(name = "done")
    @Schema(description = "Indica si el proyecto está completado o no")
    private Boolean done;

    @Column(name = "done_date")
    @Schema(description = "Fecha en la cual se completó el proyecto")
    private LocalDateTime doneDate;

    @Column(name = "purpose", length = 256)
    @Size(min = 3, max = 256, message = "Purpose debe tener mínimo 3 caracteres y máximo de 256")
    @Schema(description = "Propósito del proyecto")
    private String purpose;

    @Column(name = "vision", length = 256)
    @Size(min = 3, max = 256, message = "Vision debe tener mínimo 3 caracteres y máximo de 256")
    @Schema(description = "Visión del proyecto")
    private String vision;

    @Column(name = "brainstorming", length = 256)
    @Size(min = 3, max = 256, message = "Brainstorming debe tener mínimo 3 caracteres y máximo de 256")
    @Schema(description = "Lluvia de ideas que servirán para delimitar el proyecto")
    private String brainstorming;

    @Column(name = "organizing")
    @Size(min = 3, max = 256, message = "Organizing debe tener mínimo 3 caracteres y máximo de 256")
    @Schema(description = "De qué manera se organizará o coordinará el proyecto")
    private String organizing;

    @Lob
    @Type(type = "text")
    @Column(name = "notes")
    @Schema(description = "Notas adicionales en formato Markdown del proyecto")
    private String notes;
}
