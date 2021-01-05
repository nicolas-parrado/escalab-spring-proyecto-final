package escalab.spring.nparrado.backend_spring.service.impl;

import escalab.spring.nparrado.backend_spring.model.Attachment;
import escalab.spring.nparrado.backend_spring.repo.IAttachmentRepo;
import escalab.spring.nparrado.backend_spring.service.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentServiceImpl implements IAttachmentService {

    @Autowired
    private IAttachmentRepo repo;

    @Override
    public Attachment registrar(Attachment obj) {
        return repo.save(obj);
    }

    @Override
    public Attachment modificar(Attachment obj) {
        return repo.save(obj);
    }

    @Override
    public List<Attachment> listar() {
        return repo.findAll();
    }

    @Override
    public Attachment leerPorId(Integer id) {
        Optional<Attachment> op = repo.findById(id);
        return op.orElse(null);
    }

    @Override
    public boolean eliminar(Integer id) {
        repo.deleteById(id);
        return true;
    }

}
