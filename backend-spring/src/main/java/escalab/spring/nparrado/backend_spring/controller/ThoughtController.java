package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.Thought;
import escalab.spring.nparrado.backend_spring.model.Usuario;
import escalab.spring.nparrado.backend_spring.service.IThoughtService;
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
@RequestMapping("/thoughts")
@Tag(name = "Thoughts", description = "API de pensamientos (Thoughts)")
public class ThoughtController {

    @Autowired
    private IThoughtService service;

    @Operation(summary = "Lista todos los pensamientos (Thoughts)", description = "Lista todos los pensamientos sin filtros.", tags = {"Thought"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<Thought>> listar() {
        List<Thought> list = service.listar();
        CollectionModel<Thought> model = CollectionModel.of(list);
        for (Thought t : model) {
            agregarLinkThought(t);
        }
        model.add(linkTo(methodOn(ThoughtController.class).listar()).withSelfRel());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation( summary = "Obtiene información de un pensamiento (Thought)",
                description = "Obteniene la información de un pensamiento (Thought) buscando con su ID.",
                tags = {"Thought"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Thought.class))),
            @ApiResponse(responseCode = "404", description = "Thought no encontrado")})
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Thought> listarPorId(@PathVariable("id") Integer id) {
        Thought thought = service.leerPorId(id);
        if (thought == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        agregarLinkThought(thought);

        return new ResponseEntity<>(thought, HttpStatus.OK);
    }

    @Operation(summary = "Guarda un nuevo pensamiento (Thought)", description = "Guarda la información de un nuevo pensamiento.", tags = {"Thought"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Thought.class)))})
    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Thought thought) {
        if (thought.getCreatedDate() == null) {
            thought.setCreatedDate(LocalDateTime.now());
        }
        Thought t = service.registrar(thought);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(thought.getIdThought()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modifica todos los datos de pensamiento (Thought)", description = "Modifica los datos de un pensamiento.", tags = {"Thought"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Thought.class)))})
    @PutMapping
    public ResponseEntity<Thought> modificar(@Valid @RequestBody Thought thought) {
        service.modificar(thought);
        agregarLinkThought(thought);
        return new ResponseEntity<>(thought, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un pensamiento (Thought)", description = "Elimina un pensamiento (Thought) buscando por su ID.", tags = {"Thought"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Thought.class))),
            @ApiResponse(responseCode = "404", description = "Thought no encontrado")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
        Thought thought = service.leerPorId(id);
        if (thought == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Función para incorporar los links HATEOAS a un Thought cualquiera
     *
     * @param thought Pensamiento al que se le incorporarán los links de HATEOAS
     */
    static public void agregarLinkThought(Thought thought) {
        thought.add(linkTo(methodOn(ThoughtController.class).listarPorId(thought.getIdThought())).withSelfRel());

        if ( thought.getTopic() != null ) {
            thought.add(linkTo(methodOn(TopicController.class).listarPorId(thought.getTopic().getIdTopic())).withRel(
                    "topic"));
        }

    }
}
