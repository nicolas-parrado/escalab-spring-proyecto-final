package escalab.spring.nparrado.backend_spring.repo;

import escalab.spring.nparrado.backend_spring.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProjectRepo extends JpaRepository<Project, Integer> {
}
