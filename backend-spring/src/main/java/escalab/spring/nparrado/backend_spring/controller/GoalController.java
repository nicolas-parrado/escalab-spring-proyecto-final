package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.Goal;
import escalab.spring.nparrado.backend_spring.service.IGoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/goals")
@Tag(name = "Goals", description = "API de objetivos (Goals)")
public class GoalController {

    @Autowired
    private IGoalService service;


    @Operation(summary = "Lista todos los objetivos (Goals)", description = "Lista todos los objetivos sin filtros.", tags = {"Goals"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<Goal>> listar() {
        List<Goal> list = service.listar();
        CollectionModel<Goal> model = CollectionModel.of(list);
        for (Goal a : model) {
            agregarLinkGoal(a);
        }
        model.add(linkTo(methodOn(GoalController.class).listar()).withSelfRel());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation( summary = "Obtiene información de un objetivo (Goal)",
            description = "Obtiene la información de un objetivo (Goal) buscando por su ID.",
            tags = {"Goals"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Goal.class))),
            @ApiResponse(responseCode = "404", description = "Goal no encontrada")})
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Goal> listarPorId(@PathVariable("id") Integer id) {
        Goal goal = service.leerPorId(id);
        if (goal == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        agregarLinkGoal(goal);

        return new ResponseEntity<>(goal, HttpStatus.OK);
    }

    @Operation(summary = "Guarda un nuevo objetivo (Goal)", description = "Guarda la información de un nuevo objetivo.", tags = {"Goals"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Goal.class)))})
    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Goal goal) {
        if (goal.getCreatedDate() == null) {
            goal.setCreatedDate(LocalDateTime.now());
        }
        Goal a = service.registrar(goal);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getIdGoal()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modifica todos los datos de un objetivo (Goal)", description = "Modifica los datos de un objetivo.", tags = {"Goals"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Goal.class)))})
    @PutMapping
    public ResponseEntity<Goal> modificar(@Valid @RequestBody Goal goal) {
        service.modificar(goal);
        agregarLinkGoal(goal);
        return new ResponseEntity<>(goal, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un objetivo (Goal)", description = "Elimina un objetivo (Goal) buscando por su ID.", tags = {"Goals"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Goal.class))),
            @ApiResponse(responseCode = "404", description = "Goal no encontrada")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
        Goal goal = service.leerPorId(id);
        if (goal == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Función para incorporar los links HATEOAS a un Goal cualquiera
     *
     * @param goal Adjunto al que se le incorporarán los links de HATEOAS
     */
    static public void agregarLinkGoal(Goal goal) {
        goal.add(linkTo(methodOn(GoalController.class).listarPorId(goal.getIdGoal())).withSelfRel());

        // TODO: agregar links

    }

}
