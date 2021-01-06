package escalab.spring.nparrado.backend_spring.repo;

import escalab.spring.nparrado.backend_spring.model.Someday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISomedayRepo extends JpaRepository<Someday, Integer> {
}
