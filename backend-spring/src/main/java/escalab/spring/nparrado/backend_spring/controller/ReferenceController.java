package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.Reference;
import escalab.spring.nparrado.backend_spring.service.IReferenceService;
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
@RequestMapping("/references")
@Tag(name = "References", description = "API de referenciaes (References)")
public class ReferenceController {

    @Autowired
    private IReferenceService service;


    @Operation(summary = "Lista todas las referenciaes (References)", description = "Lista todas las referenciaes sin filtros.", tags = {"References"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<Reference>> listar() {
        List<Reference> list = service.listar();
        CollectionModel<Reference> model = CollectionModel.of(list);
        for (Reference a : model) {
            agregarLinkReference(a);
        }
        model.add(linkTo(methodOn(ReferenceController.class).listar()).withSelfRel());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation( summary = "Obtiene información de una referencia (Reference)",
            description = "Obtiene la información de una referencia (Reference) buscando por su ID.",
            tags = {"References"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Reference.class))),
            @ApiResponse(responseCode = "404", description = "Reference no encontrada")})
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reference> listarPorId(@PathVariable("id") Integer id) {
        Reference reference = service.leerPorId(id);
        if (reference == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        agregarLinkReference(reference);

        return new ResponseEntity<>(reference, HttpStatus.OK);
    }

    @Operation(summary = "Guarda una nueva referencia (Reference)", description = "Guarda la información de una nueva referencia.", tags = {"References"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Reference.class)))})
    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Reference reference) {
        if (reference.getCreatedDate() == null) {
            reference.setCreatedDate(LocalDateTime.now());
        }
        Reference a = service.registrar(reference);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getIdReference()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modifica todos los datos de una referencia (Reference)", description = "Modifica los datos de una referencia.", tags = {"References"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Reference.class)))})
    @PutMapping
    public ResponseEntity<Reference> modificar(@Valid @RequestBody Reference reference) {
        service.modificar(reference);
        agregarLinkReference(reference);
        return new ResponseEntity<>(reference, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una referencia (Reference)", description = "Elimina una referencia (Reference) buscando por su ID.", tags = {"References"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Reference.class))),
            @ApiResponse(responseCode = "404", description = "Reference no encontrada")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
        Reference reference = service.leerPorId(id);
        if (reference == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Función para incorporar los links HATEOAS a un Reference cualquiera
     *
     * @param reference Referencia a la que se le incorporarán los links de HATEOAS
     */
    static public void agregarLinkReference(Reference reference) {
        reference.add(linkTo(methodOn(ReferenceController.class).listarPorId(reference.getIdReference())).withSelfRel());

        // TODO: agregar links

    }
}
