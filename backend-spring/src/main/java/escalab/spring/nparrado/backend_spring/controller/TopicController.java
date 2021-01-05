package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.Thought;
import escalab.spring.nparrado.backend_spring.model.Topic;
import escalab.spring.nparrado.backend_spring.service.ITopicService;
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
@RequestMapping("/topics")
@Tag(name = "Topics", description = "API de Tópicos (Topics)")
public class TopicController {

    @Autowired
    private ITopicService service;

    @Operation(summary = "Lista todos los tópicos (Topic)", description = "Lista todos los tópicos sin filtros",
            tags = {"Topics"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<Topic>> listar() {

        List<Topic> list = service.listar();
        CollectionModel<Topic> model = CollectionModel.of(list);
        for (Topic t : model) {
            agregarLinkTopic(t);
        }
        model.add(linkTo(methodOn(TopicController.class).listar()).withSelfRel());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene información de un tópico (Topic)",
            description = "Obtiene la información de un tópico (Topic) buscando con su ID.",
            tags = {"Topics"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Topic.class))),
            @ApiResponse(responseCode = "404", description = "Topic no encontrado")})
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Topic> listarPorId(@PathVariable("id") Integer id) {
        Topic topic = service.leerPorId(id);
        if (topic == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        agregarLinkTopic(topic);

        return new ResponseEntity<>(topic, HttpStatus.OK);
    }


    @Operation(summary = "Guarda un nuevo tópico (Topic)", description = "Guarda la información de un nuevo tópico.",
            tags = {"Topics"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitoas",
                    content = @Content(schema = @Schema(implementation = Topic.class)))
    })
    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Topic topic) {
        Topic t = service.registrar(topic);

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(topic.getIdTopic()).toUri();
        return ResponseEntity.created(location).build();
    }


    @Operation(summary = "Modifica todos los datos de un tópico (Topic)", description = "Modifica los datos de un " +
            "tópico.", tags = {"Topics"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa", content = @Content(schema =
            @Schema(implementation = Topic.class)))
    })
    @PutMapping
    public ResponseEntity<Topic> modificar(@Valid @RequestBody Topic topic) {
        service.modificar(topic);
        agregarLinkTopic(topic);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un tópico (Topic)", description = "Elimina un tópico (Topic) buscando por su ID",
            tags = {"Topics"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa", content = @Content(schema =
            @Schema(implementation = Topic.class))),
            @ApiResponse(responseCode = "404", description = "Topic no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
        Topic topic = service.leerPorId(id);
        if (topic == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Función para incorporar los links HATEOAS a un Topic cualquiera
     *
     * @param topic Tópico al que se le incorporarán los links de HATEOAS
     */
    static public void agregarLinkTopic(Topic topic) {
        topic.add(linkTo(methodOn(TopicController.class).listarPorId(topic.getIdTopic())).withSelfRel());

        //TODO: agregar links

    }

}
