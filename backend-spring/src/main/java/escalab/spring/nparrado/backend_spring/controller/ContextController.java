package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.Context;
import escalab.spring.nparrado.backend_spring.service.IContextService;
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
@RequestMapping("/contexts")
@Tag(name = "Contexts", description = "API de contextos (Contexts)")
public class ContextController {

    @Autowired
    private IContextService service;


    @Operation(summary = "Lista todos los contextos (Contexts)", description = "Lista todos los contextos sin filtros.", tags = {"Contexts"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<Context>> listar() {
        List<Context> list = service.listar();
        CollectionModel<Context> model = CollectionModel.of(list);
        for (Context context : model) {
            agregarLinkContext(context);
        }
        model.add(linkTo(methodOn(ContextController.class).listar()).withSelfRel());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation( summary = "Obtiene información de un contexto (Context)",
            description = "Obtiene la información de un contexto (Context) buscando por su ID.",
            tags = {"Contexts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Context.class))),
            @ApiResponse(responseCode = "404", description = "Context no encontrada")})
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Context> listarPorId(@PathVariable("id") Integer id) {
        Context context = service.leerPorId(id);
        if (context == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        agregarLinkContext(context);

        return new ResponseEntity<>(context, HttpStatus.OK);
    }

    @Operation(summary = "Guarda un nuevo contexto (Context)", description = "Guarda la información de un nuevo contexto.", tags = {"Contexts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Context.class)))})
    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Context context) {
        Context a = service.registrar(context);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getIdContext()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modifica todos los datos de un contexto (Context)", description = "Modifica los datos de un contexto.", tags = {"Contexts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Context.class)))})
    @PutMapping
    public ResponseEntity<Context> modificar(@Valid @RequestBody Context context) {
        service.modificar(context);
        agregarLinkContext(context);
        return new ResponseEntity<>(context, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un contexto (Context)", description = "Elimina un contexto (Context) buscando por su ID.", tags = {"Contexts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Context.class))),
            @ApiResponse(responseCode = "404", description = "Context no encontrado")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
        Context context = service.leerPorId(id);
        if (context == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Función para incorporar los links HATEOAS a un Context cualquiera
     *
     * @param context Contexto al que se le incorporarán los links de HATEOAS
     */
    static public void agregarLinkContext(Context context) {
        context.add(linkTo(methodOn(ContextController.class).listarPorId(context.getIdContext())).withSelfRel());

        // TODO: agregar links

    }

}
