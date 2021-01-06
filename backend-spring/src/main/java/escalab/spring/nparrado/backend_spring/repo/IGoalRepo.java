package escalab.spring.nparrado.backend_spring.repo;

import escalab.spring.nparrado.backend_spring.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGoalRepo extends JpaRepository<Goal, Integer> {
}
