package escalab.spring.nparrado.backend_spring.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Tag(name = "ActionsProject", description = "Acciones por Proyecto")
public class ActionsProject extends RepresentationModel<ActionsProject> {

    private String projectName;
    private Integer actionsCount;

}
