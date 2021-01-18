package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.dto.ActionsProject;
import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.Action;
import escalab.spring.nparrado.backend_spring.model.Project;
import escalab.spring.nparrado.backend_spring.service.IActionService;
import escalab.spring.nparrado.backend_spring.service.IProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/projects")
@Tag(name = "Projects", description = "API de proyectos (Projects)")
public class ProjectController {

    @Autowired
    private IProjectService service;

    @Autowired
    private IActionService actionService;


    private Logger log = LoggerFactory.getLogger(ProjectController.class);

    @Operation(summary = "Lista todos los proyectos (Projects)", description = "Lista todos los proyectos sin filtros.", tags = {"Projects"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<Project>> listar() {
        List<Project> list = service.listar();
        CollectionModel<Project> model = CollectionModel.of(list);
        for (Project a : model) {
            agregarLinkProject(a);
        }
        model.add(linkTo(methodOn(ProjectController.class).listar()).withSelfRel());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation( summary = "Obtiene información de un proyecto (Project)",
            description = "Obtiene la información de un proyecto (Project) buscando por su ID.",
            tags = {"Projects"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project no encontrada")})
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> listarPorId(@PathVariable("id") Integer id) {
        Project project = service.leerPorId(id);
        if (project == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        agregarLinkProject(project);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @Operation(summary = "Guarda un nuevo proyecto (Project)", description = "Guarda la información de un nuevo proyecto.", tags = {"Projects"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Project.class)))})
    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Project project) {
        if (project.getCreatedDate() == null) {
            project.setCreatedDate(LocalDateTime.now());
        }
        Project a = service.registrar(project);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getIdProject()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modifica todos los datos de un proyecto (Project)", description = "Modifica los datos de un proyecto.", tags = {"Projects"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Project.class)))})
    @PutMapping
    public ResponseEntity<Project> modificar(@Valid @RequestBody Project project) {
        service.modificar(project);
        agregarLinkProject(project);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un proyecto (Project)", description = "Elimina un proyecto (Project) buscando por su ID.", tags = {"Projects"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project no encontrada")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
        Project project = service.leerPorId(id);
        if (project == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Lista todos los proyectos (Projects) con el número de acciones que tiene cada uno",
            description = "Lista todos los proyectos sin filtros con el número de acciones que tiene cada uno.",
            tags = {"Projects"})
    @GetMapping("/actions")
    public ResponseEntity<CollectionModel<ActionsProject>> accionesProyecto() {
        log.info("Inicia");
        List<Project> proyectos = service.listar();
        log.info("Obtiene proyectos: " + proyectos.size());
        List<ActionsProject> actionsProjects = new ArrayList<>();

        for (Project p: proyectos) {
            log.info("Lee proyecto " + p.getIdProject() + " : " + p.getName());
            ActionsProject ap = new ActionsProject();
            ap.setProjectName(p.getName());
            log.info("Busca las acciones");
            ap.setActionsCount(p.getActions() == null ? 0 : p.getActions().size());
            ap.add(linkTo(methodOn(ProjectController.class).listarPorId(p.getIdProject())).withSelfRel());
            actionsProjects.add(ap);
            log.info("Agrega correctamente las acciones");
        }

        CollectionModel<ActionsProject> model = CollectionModel.of(actionsProjects);
        model.add(linkTo(methodOn(ProjectController.class).accionesProyecto()).withSelfRel());

        log.info("Retorna la respuesta");
        return new ResponseEntity<>(model, HttpStatus.OK);

    }


    @Operation( summary = "Obtiene información de las acciones (Actions) de un proyecto (Project)",
            description = "Obtiene la información de las acciones (Actions) de un proyecto (Project) buscando por su ID.",
            tags = {"Projects"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejecución exitosa",
                    content = @Content(schema = @Schema(implementation = Action.class))),
            @ApiResponse(responseCode = "404", description = "Project no encontrada")})
    @GetMapping("/{id}/actions")
    public ResponseEntity<CollectionModel<Action>> acciones_por_id(@PathVariable("id") Integer id) {
        Project project = service.leerPorId(id);
        if( project == null ) {
            throw new ModeloNotFoundException("Id NO ENCONTRADO " + id);
        }

        for(Action action : project.getActions()) {
            ActionController.agregarLinkAction(action);
        }

        CollectionModel<Action> model = CollectionModel.of(project.getActions());
        model.add(linkTo(methodOn(ProjectController.class).acciones_por_id(id)).withSelfRel());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }


    @Operation( summary = "Agrega una acción a este proyecto",
    description = "Agrega una nueva acción a  este proyecto usando du ID",
    tags = {"Projects"})
    @PostMapping("/{id_project}/action/{id_action}")
    public ResponseEntity<Project> addAction(@PathVariable("id_project") Integer idProject, @PathVariable("id_action") Integer idAction) {
        Project p = service.leerPorId(idProject);

        if( p == null ){
            throw new ModeloNotFoundException("ID de proyecto no encontrado " + idProject);
        }

        Action a = actionService.leerPorId(idAction);
        if( a == null ){
            throw new ModeloNotFoundException("ID de action no encontrado " + idAction);
        }

        p.getActions().add(a);
        p = service.modificar(p);
        return new ResponseEntity<>(p, HttpStatus.OK);

    }


    /**
     * Función para incorporar los links HATEOAS a un Project cualquiera
     *
     * @param project Proyecto al que se le incorporarán los links de HATEOAS
     */
    static public void agregarLinkProject(Project project) {
        project.add(linkTo(methodOn(ProjectController.class).listarPorId(project.getIdProject())).withSelfRel());

        // Links adicionales links
        project.add(linkTo(methodOn(ProjectController.class).acciones_por_id(project.getIdProject())).withRel("acciones"));


    }

}
