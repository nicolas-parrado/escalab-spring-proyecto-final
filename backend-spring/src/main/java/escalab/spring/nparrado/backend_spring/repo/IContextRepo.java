package escalab.spring.nparrado.backend_spring.repo;

import escalab.spring.nparrado.backend_spring.model.Context;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContextRepo extends JpaRepository<Context, Integer> {
}
