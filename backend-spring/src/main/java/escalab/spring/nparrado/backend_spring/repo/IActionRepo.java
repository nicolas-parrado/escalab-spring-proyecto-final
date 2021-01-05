package escalab.spring.nparrado.backend_spring.repo;

import escalab.spring.nparrado.backend_spring.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IActionRepo extends JpaRepository<Action, Integer> {
}
