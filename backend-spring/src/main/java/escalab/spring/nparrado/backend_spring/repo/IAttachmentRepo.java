package escalab.spring.nparrado.backend_spring.repo;

import escalab.spring.nparrado.backend_spring.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAttachmentRepo extends JpaRepository<Attachment, Integer> {
}
