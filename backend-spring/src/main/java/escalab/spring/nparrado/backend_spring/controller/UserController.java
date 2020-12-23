package escalab.spring.nparrado.backend_spring.controller;

import escalab.spring.nparrado.backend_spring.exception.ModeloNotFoundException;
import escalab.spring.nparrado.backend_spring.model.Usuario;
import escalab.spring.nparrado.backend_spring.service.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "API de Usuarios")
public class UserController {
	
	@Autowired
	private IUsuarioService userService;

	@Operation(summary = "Lista todos los usuarios", description = "Lista todos los usuarios existentes", tags = { "Usuarios" })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Usuario>> listar(){
		List<Usuario> lista = userService.listar();
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@Operation(summary = " Obtiene usuario por ID", description = "Retorna un único usuario", tags = { "Usuarios" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ejecución exitosa",
					content = @Content(schema = @Schema(implementation = Usuario.class))),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado") })
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> listarPorId(@PathVariable("id") Integer id){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
			Usuario obj = userService.leerPorId(id);
			if(obj.getIdUsuario() == null) {
				throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
			}
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new Usuario(), HttpStatus.UNAUTHORIZED);
		}
		
	}

}
