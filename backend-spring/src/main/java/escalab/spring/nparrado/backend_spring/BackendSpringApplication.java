package escalab.spring.nparrado.backend_spring;

import escalab.spring.nparrado.backend_spring.model.Action;
import escalab.spring.nparrado.backend_spring.model.Project;
import escalab.spring.nparrado.backend_spring.model.Rol;
import escalab.spring.nparrado.backend_spring.model.Usuario;
import escalab.spring.nparrado.backend_spring.repo.IActionRepo;
import escalab.spring.nparrado.backend_spring.repo.IProjectRepo;
import escalab.spring.nparrado.backend_spring.repo.IRolRepo;
import escalab.spring.nparrado.backend_spring.repo.IUsuarioRepo;
import org.apache.tomcat.jni.Local;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BackendSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendSpringApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner(IUsuarioRepo usuarioRepo, IRolRepo rolRepo, IProjectRepo projectRepo, IActionRepo actionRepo) {
		return args -> {prepare(usuarioRepo, rolRepo, projectRepo, actionRepo);};
	}

	/**
	 * Creación de un usuario básico para poder usar los servicios con autenticación
	 * El usuario es nparrado con pass 123
	 * @param usuarioRepo repositorio de usuarios
	 * @param rolRepo repositorio de roles
	 */
	private void prepare(IUsuarioRepo usuarioRepo, IRolRepo rolRepo, IProjectRepo projectRepo, IActionRepo actionRepo) {


		Rol rol = new Rol();
		rol.setNombre("ADMIN");
		rol = rolRepo.save(rol);

		List<Rol> roles = new ArrayList<>();
		roles.add(rol);

		Usuario usuario = new Usuario();
		usuario.setUsername("nparrado");
		usuario.setPassword("$2a$10$ju20i95JTDkRa7Sua63JWOChSBc0MNFtG/6Sps2ahFFqN.HCCUMW.");
		usuario.setEnabled(true);
		usuario.setRoles(roles);

		usuarioRepo.save(usuario);

		// Se agrega un proyecto con acciones

		Project p = new Project();
		p.setDone(false);
		p.setName("Proyecto Escalab");
		p.setCreatedDate(LocalDateTime.now());

		p = projectRepo.save(p);

		Action action = new Action();
		action.setName("Tarea 1");
		action.setCreatedDate(LocalDateTime.now());
		action.setProject(p);
		actionRepo.save(action);
	}
}
