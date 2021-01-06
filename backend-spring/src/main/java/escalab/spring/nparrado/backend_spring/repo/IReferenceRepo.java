package escalab.spring.nparrado.backend_spring.repo;

import escalab.spring.nparrado.backend_spring.model.Reference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReferenceRepo extends JpaRepository<Reference, Integer> {
}
