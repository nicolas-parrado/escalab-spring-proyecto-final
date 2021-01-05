package escalab.spring.nparrado.backend_spring.repo;

import escalab.spring.nparrado.backend_spring.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITopicRepo extends JpaRepository<Topic, Integer> {
}
