package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.Delegate;
import escalab.spring.nparrado.backend_spring.service.IDelegateService;
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
@RequestMapping("/delegates")
@Tag(name = "Delegates", description = "API de delegados (Delegates)")
public class DelegateController {

    @Autowired
    private IDelegateService service;


    @Operation(summary = "Lista todos los delegados (Delegates)", description = "Lista todos los delegados sin filtros.", tags = {"Delegates"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<Delegate>> listar() {
        List<Delegate> list = service.listar();
        CollectionModel<Delegate> model = CollectionModel.of(list);
        for (Delegate a : model) {
            agregarLinkDelegate(a);
        }
        model.add(linkTo(methodOn(DelegateController.class).listar()).withSelfRel());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation( summary = "Obtiene información de un delegado (Delegate)",
            description = "Obtiene la información de un delegado (Delegate) buscando por su ID.",
            tags = {"Delegates"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Delegate.class))),
            @ApiResponse(responseCode = "404", description = "Delegate no encontrada")})
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Delegate> listarPorId(@PathVariable("id") Integer id) {
        Delegate delegate = service.leerPorId(id);
        if (delegate == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        agregarLinkDelegate(delegate);

        return new ResponseEntity<>(delegate, HttpStatus.OK);
    }

    @Operation(summary = "Guarda un nuevo delegado (Delegate)", description = "Guarda la información de un nuevo delegado.", tags = {"Delegates"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Delegate.class)))})
    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Delegate delegate) {
        Delegate a = service.registrar(delegate);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getIdDelegate()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modifica todos los datos de un delegado (Delegate)", description = "Modifica los datos de un delegado.", tags = {"Delegates"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Delegate.class)))})
    @PutMapping
    public ResponseEntity<Delegate> modificar(@Valid @RequestBody Delegate delegate) {
        service.modificar(delegate);
        agregarLinkDelegate(delegate);
        return new ResponseEntity<>(delegate, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un delegado (Delegate)", description = "Elimina un delegado (Delegate) buscando por su ID.", tags = {"Delegates"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Delegate.class))),
            @ApiResponse(responseCode = "404", description = "Delegate no encontrada")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
        Delegate delegate = service.leerPorId(id);
        if (delegate == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Función para incorporar los links HATEOAS a un Delegate cualquiera
     *
     * @param delegate Adjunto al que se le incorporarán los links de HATEOAS
     */
    static public void agregarLinkDelegate(Delegate delegate) {
        delegate.add(linkTo(methodOn(DelegateController.class).listarPorId(delegate.getIdDelegate())).withSelfRel());

        // TODO: agregar links

    }

}
