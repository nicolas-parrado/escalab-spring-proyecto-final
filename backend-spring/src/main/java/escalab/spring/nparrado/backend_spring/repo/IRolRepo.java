package escalab.spring.nparrado.backend_spring.repo;

import escalab.spring.nparrado.backend_spring.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepo extends JpaRepository<Rol, Integer> {
}
