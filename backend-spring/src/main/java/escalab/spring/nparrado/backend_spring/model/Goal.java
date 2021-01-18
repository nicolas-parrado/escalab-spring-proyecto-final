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
@Table(name = "goal")
@Schema(description = "Objetivo específico que se persigue")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "idGoal"
)
public class Goal extends RepresentationModel<Goal> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGoal;

    @ManyToOne
    @JoinColumn(name = "id_goal_level")
    @Schema(description = "Nivel del objetivo")
    @JsonIgnore
    private GoalLevel goalLevel;

    @Column(name = "name", length = 70)
    @Schema(description = "nombre corto del objetivo")
    @Size(min = 3, max = 70, message = "Nombre debe tener mínimo 3 caracteres y máximo de 70")
    private String name;

    @Column(name = "start_date")
    @Schema(description = "Fecha de inicio del objetivo")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @Schema(description = "Fecha de entrega/vencimiento del objetivo")
    private LocalDateTime endDate;

    @Column(name = "vision", length = 256)
    @Size(max = 256, message = "Vision debe tener como máximo 256 caracteres")
    @Schema(description = "Fecha de entrega/vencimiento del objetivo")
    private String vision;

    @Column(name = "accountability")
    @Size(max = 256, message = "Accountability debe tener como máximo 256 caracteres")
    @Schema(description = "Responsabilidad del objetivo. De que manera se va a presionar para realizarlo")
    private String accountability;

    @Column(name = "rewards")
    @Size(max = 256, message = "Rewards debe tener como máximo 256 caracteres")
    @Schema(description = "Recompensa cuando se finalice el objetivo")
    private String rewards;

    @Column(name = "obstacles")
    @Size(max = 256, message = "Obstacles debe tener como máximo 256 caracteres")
    @Schema(description = "Obstáculos que se ven en el objetivo")
    private String obstacles;

    @Column(name = "support")
    @Size(max = 256, message = "Suppert debe tener como máximo 256 caracteres")
    @Schema(description = "Cuales son las redes de apoyo o soporte que se tienen para cumplir el objetivo")
    private String support;

    @Column(name = "brainstorming")
    @Size(max = 256, message = "Brainstorming debe tener como máximo 256 caracteres")
    @Schema(description = "Lluvia de ideas para cumplir el objetivo")
    private String brainstorming;

    @Column(name = "achieved")
    @Schema(description = "Indica si el objetivo fue logrado (true) o no (false)")
    private Boolean achieved;

    @Column(name = "achieved_date_time")
    @Schema(description = "Fecha en la cual se logró el objetivo")
    private LocalDateTime achievedDateTime;

    @Column(name = "created_date")
    @Schema(description = "Fecha de creación del objetivo")
    private LocalDateTime createdDate;

    @Lob
    @Type(type = "text")
    @Column(name = "notes")
    @Schema(description = "Notas adicionales en formato Markdown del objetivo")
    private String notes;
}
