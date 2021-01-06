package escalab.spring.nparrado.backend_spring.repo;

import escalab.spring.nparrado.backend_spring.model.GoalLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGoalLevelRepo extends JpaRepository<GoalLevel, Integer> {
}
