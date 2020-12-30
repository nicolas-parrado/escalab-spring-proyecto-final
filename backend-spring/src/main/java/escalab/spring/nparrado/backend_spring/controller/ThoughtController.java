package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.Thought;
import escalab.spring.nparrado.backend_spring.service.IThoughtService;
import io.swagger.v3.oas.annotations.Operation;
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
public class ThoughtController {

    @Autowired
    private IThoughtService service;

    @Operation(summary = "Lista todos los pensamientos (Thoughts)", description = "Lista todos los pensamientos sin filtros", tags = { "Thought" })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<Thought>> listar() {
        List<Thought> list = service.listar();
        CollectionModel<Thought> model = CollectionModel.of(list);
        for( Thought t : model) {
            agregarLinkThought(t);
        }
        model.add(linkTo(methodOn(ThoughtController.class).listar()).withSelfRel());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Thought> listarPorId(@PathVariable("id") Integer id) {
        Thought thought = service.leerPorId(id);
        if( thought == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        agregarLinkThought(thought);

        return new ResponseEntity<>(thought, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Thought thought) {
        if(thought.getCreatedDate() == null) {
            thought.setCreatedDate(LocalDateTime.now());
        }
        Thought t = service.registrar(thought);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(thought.getIdThought()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Thought> modificar(@Valid @RequestBody Thought thought) {
        service.modificar(thought);
        agregarLinkThought(thought);
        return new ResponseEntity<>(thought, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
        Thought thought = service.leerPorId(id);
        if(thought == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void agregarLinkThought(Thought t){
        t.add(linkTo(methodOn(ThoughtController.class).listarPorId(t.getIdThought())).withSelfRel());

        //TODO: Agregar link de Topic
    }
}
