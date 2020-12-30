package escalab.spring.nparrado.backend_spring.repo;

import escalab.spring.nparrado.backend_spring.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepo extends JpaRepository<Usuario, Integer> {

	Usuario findOneByUsername(String username);

}
