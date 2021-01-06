package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.Someday;
import escalab.spring.nparrado.backend_spring.service.ISomedayService;
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
@RequestMapping("/somedays")
@Tag(name = "Somedays", description = "API de acciones (Somedays)")
public class SomedayController {

    @Autowired
    private ISomedayService service;


    @Operation(summary = "Lista todas las tareas que se realizarán algún día (Somedays)", description = "Lista todas las tareas que se realizarán algún día sin filtros.", tags = {"Somedays"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<Someday>> listar() {
        List<Someday> list = service.listar();
        CollectionModel<Someday> model = CollectionModel.of(list);
        for (Someday a : model) {
            agregarLinkSomeday(a);
        }
        model.add(linkTo(methodOn(SomedayController.class).listar()).withSelfRel());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation( summary = "Obtiene información de una tarea a realizar algún día (Someday)",
            description = "Obtiene la información de una tarea a realizar algún día (Someday) buscando por su ID.",
            tags = {"Somedays"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Someday.class))),
            @ApiResponse(responseCode = "404", description = "Someday no encontrada")})
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Someday> listarPorId(@PathVariable("id") Integer id) {
        Someday someday = service.leerPorId(id);
        if (someday == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        agregarLinkSomeday(someday);

        return new ResponseEntity<>(someday, HttpStatus.OK);
    }

    @Operation(summary = "Guarda una nueva tarea a realizar algún día (Someday)", description = "Guarda la información de una nueva tareas a realizar algún día.", tags = {"Somedays"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Someday.class)))})
    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Someday someday) {
        if (someday.getCreatedDate() == null) {
            someday.setCreatedDate(LocalDateTime.now());
        }
        Someday a = service.registrar(someday);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getIdSomeday()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modifica todos los datos de una tarea a realizar algún día (Someday)", description = "Modifica los datos de una tareas a realizar algún día.", tags = {"Somedays"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Someday.class)))})
    @PutMapping
    public ResponseEntity<Someday> modificar(@Valid @RequestBody Someday someday) {
        service.modificar(someday);
        agregarLinkSomeday(someday);
        return new ResponseEntity<>(someday, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una tarea a realizar algún día (Someday)", description = "Elimina una tareas a realizar algún día (Someday) buscando por su ID.", tags = {"Somedays"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Someday.class))),
            @ApiResponse(responseCode = "404", description = "Someday no encontrada")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
        Someday someday = service.leerPorId(id);
        if (someday == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Función para incorporar los links HATEOAS a un Someday cualquiera
     *
     * @param someday Acción a la que se le incorporarán los links de HATEOAS
     */
    static public void agregarLinkSomeday(Someday someday) {
        someday.add(linkTo(methodOn(SomedayController.class).listarPorId(someday.getIdSomeday())).withSelfRel());

        // TODO: agregar links

    }
}
