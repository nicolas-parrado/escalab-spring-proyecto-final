package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.Action;
import escalab.spring.nparrado.backend_spring.service.IActionService;
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
@RequestMapping("/actions")
@Tag(name = "Actions", description = "API de acciones (Actions)")
public class ActionController {

    @Autowired
    private IActionService service;

    /**
     * Función para incorporar los links HATEOAS a un Action cualquiera
     *
     * @param action Acción a la que se le incorporarán los links de HATEOAS
     */
    static public void agregarLinkThought(Action action) {
        action.add(linkTo(methodOn(ActionController.class).listarPorId(action.getIdAction())).withSelfRel());

        // TODO: agregar links

    }

    @Operation(summary = "Lista todas las acciones (Actions)", description = "Lista todas las acciones sin filtros.", tags = {"Actions"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<Action>> listar() {
        List<Action> list = service.listar();
        CollectionModel<Action> model = CollectionModel.of(list);
        for (Action a : model) {
            agregarLinkThought(a);
        }
        model.add(linkTo(methodOn(ActionController.class).listar()).withSelfRel());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation( summary = "Obtiene información de una acción (Action)",
            description = "Obtiene la información de una acción (Action) buscando por su ID.",
            tags = {"Actions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Action.class))),
            @ApiResponse(responseCode = "404", description = "Action no encontrada")})
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Action> listarPorId(@PathVariable("id") Integer id) {
        Action action = service.leerPorId(id);
        if (action == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        agregarLinkThought(action);

        return new ResponseEntity<>(action, HttpStatus.OK);
    }

    @Operation(summary = "Guarda una nueva acción (Action)", description = "Guarda la información de una nueva acción.", tags = {"Actions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Action.class)))})
    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Action action) {
        if (action.getCreatedDate() == null) {
            action.setCreatedDate(LocalDateTime.now());
        }
        Action a = service.registrar(action);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getIdAction()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modifica todos los datos de una acción (Action)", description = "Modifica los datos de una acción.", tags = {"Actions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Action.class)))})
    @PutMapping
    public ResponseEntity<Action> modificar(@Valid @RequestBody Action action) {
        service.modificar(action);
        agregarLinkThought(action);
        return new ResponseEntity<>(action, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una acción (Action)", description = "Elimina una acción (Action) buscando por su ID.", tags = {"Actions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Action.class))),
            @ApiResponse(responseCode = "404", description = "Action no encontrada")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
        Action action = service.leerPorId(id);
        if (action == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
