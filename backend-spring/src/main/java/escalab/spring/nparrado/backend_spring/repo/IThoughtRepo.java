package escalab.spring.nparrado.backend_spring.repo;

import escalab.spring.nparrado.backend_spring.model.Thought;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IThoughtRepo extends JpaRepository<Thought, Integer> {
}
