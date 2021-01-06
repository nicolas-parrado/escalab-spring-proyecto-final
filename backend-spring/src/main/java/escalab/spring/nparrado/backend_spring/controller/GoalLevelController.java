package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.GoalLevel;
import escalab.spring.nparrado.backend_spring.service.IGoalLevelService;
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
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/goal_levels")
@Tag(name = "GoalLevels", description = "API de nivel de objetivos (GoalLevels)")
public class GoalLevelController {

    @Autowired
    private IGoalLevelService service;


    @Operation(summary = "Lista todos los nivel de objetivos (GoalLevels)", description = "Lista todos los nivel de objetivos sin filtros.", tags = {"GoalLevels"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<GoalLevel>> listar() {
        List<GoalLevel> list = service.listar();
        CollectionModel<GoalLevel> model = CollectionModel.of(list);
        for (GoalLevel a : model) {
            agregarLinkGoalLevel(a);
        }
        model.add(linkTo(methodOn(GoalLevelController.class).listar()).withSelfRel());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation( summary = "Obtiene información de un nivel de objetivo (GoalLevel)",
            description = "Obtiene la información de un nivel de objetivo (GoalLevel) buscando por su ID.",
            tags = {"GoalLevels"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = GoalLevel.class))),
            @ApiResponse(responseCode = "404", description = "GoalLevel no encontrada")})
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GoalLevel> listarPorId(@PathVariable("id") Integer id) {
        GoalLevel goalLevel = service.leerPorId(id);
        if (goalLevel == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        agregarLinkGoalLevel(goalLevel);

        return new ResponseEntity<>(goalLevel, HttpStatus.OK);
    }

    @Operation(summary = "Guarda un nuevo nivel de objetivo (GoalLevel)", description = "Guarda la información de un nuevo nivel de objetivo.", tags = {"GoalLevels"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = GoalLevel.class)))})
    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody GoalLevel goalLevel) {
        GoalLevel a = service.registrar(goalLevel);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getIdGoalLevel()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modifica todos los datos de un nivel de objetivo (GoalLevel)", description = "Modifica los datos de un nivel de objetivo.", tags = {"GoalLevels"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = GoalLevel.class)))})
    @PutMapping
    public ResponseEntity<GoalLevel> modificar(@Valid @RequestBody GoalLevel goalLevel) {
        service.modificar(goalLevel);
        agregarLinkGoalLevel(goalLevel);
        return new ResponseEntity<>(goalLevel, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un nivel de objetivo (GoalLevel)", description = "Elimina un nivel de objetivo (GoalLevel) buscando por su ID.", tags = {"GoalLevels"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = GoalLevel.class))),
            @ApiResponse(responseCode = "404", description = "GoalLevel no encontrada")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
        GoalLevel goalLevel = service.leerPorId(id);
        if (goalLevel == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Función para incorporar los links HATEOAS a un GoalLevel cualquiera
     *
     * @param goalLevel Nivel de Objetivo al que se le incorporarán los links de HATEOAS
     */
    static public void agregarLinkGoalLevel(GoalLevel goalLevel) {
        goalLevel.add(linkTo(methodOn(GoalLevelController.class).listarPorId(goalLevel.getIdGoalLevel())).withSelfRel());

        // TODO: agregar links

    }

}
