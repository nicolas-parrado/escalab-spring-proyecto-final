package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.Attachment;
import escalab.spring.nparrado.backend_spring.service.IAttachmentService;
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
@RequestMapping("/attachments")
@Tag(name = "Attachments", description = "API de adjuntos (Attachments)")
public class AttachmentController {

    @Autowired
    private IAttachmentService service;


    @Operation(summary = "Lista todos los adjuntos (Attachments)", description = "Lista todos los adjuntos sin filtros.", tags = {"Attachments"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<Attachment>> listar() {
        List<Attachment> list = service.listar();
        CollectionModel<Attachment> model = CollectionModel.of(list);
        for (Attachment a : model) {
            agregarLinkAttachment(a);
        }
        model.add(linkTo(methodOn(AttachmentController.class).listar()).withSelfRel());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation( summary = "Obtiene información de un adjunto (Attachment)",
            description = "Obtiene la información de un adjunto (Attachment) buscando por su ID.",
            tags = {"Attachments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Attachment.class))),
            @ApiResponse(responseCode = "404", description = "Attachment no encontrada")})
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Attachment> listarPorId(@PathVariable("id") Integer id) {
        Attachment attachment = service.leerPorId(id);
        if (attachment == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        agregarLinkAttachment(attachment);

        return new ResponseEntity<>(attachment, HttpStatus.OK);
    }

    @Operation(summary = "Guarda un nuevo adjunto (Attachment)", description = "Guarda la información de un nuevo adjunto.", tags = {"Attachments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Attachment.class)))})
    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Attachment attachment) {
        if (attachment.getCreatedDate() == null) {
            attachment.setCreatedDate(LocalDateTime.now());
        }
        Attachment a = service.registrar(attachment);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getIdAttachment()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modifica todos los datos de un adjunto (Attachment)", description = "Modifica los datos de un adjunto.", tags = {"Attachments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Attachment.class)))})
    @PutMapping
    public ResponseEntity<Attachment> modificar(@Valid @RequestBody Attachment attachment) {
        service.modificar(attachment);
        agregarLinkAttachment(attachment);
        return new ResponseEntity<>(attachment, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un adjunto (Attachment)", description = "Elimina un adjunto (Attachment) buscando por su ID.", tags = {"Attachments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Attachment.class))),
            @ApiResponse(responseCode = "404", description = "Attachment no encontrada")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
        Attachment attachment = service.leerPorId(id);
        if (attachment == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Función para incorporar los links HATEOAS a un Attachment cualquiera
     *
     * @param attachment Adjunto al que se le incorporarán los links de HATEOAS
     */
    static public void agregarLinkAttachment(Attachment attachment) {
        attachment.add(linkTo(methodOn(AttachmentController.class).listarPorId(attachment.getIdAttachment())).withSelfRel());

        // TODO: agregar links

    }

}
