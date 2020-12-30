package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.Thought;
import escalab.spring.nparrado.backend_spring.service.IThoughtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/thoughts")
public class ThoughtController {

    @Autowired
    private IThoughtService service;

    @GetMapping
    public ResponseEntity<List<Thought>> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Thought> listarPorId(@PathVariable("id") Integer id) {
        Thought thought = service.leerPorId(id);
        if( thought == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

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
        return new ResponseEntity<>(service.modificar(thought), HttpStatus.OK);
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
}
