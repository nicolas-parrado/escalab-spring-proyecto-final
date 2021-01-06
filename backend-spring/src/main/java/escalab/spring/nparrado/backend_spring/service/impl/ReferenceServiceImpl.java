package escalab.spring.nparrado.backend_spring.service.impl;

import escalab.spring.nparrado.backend_spring.model.Reference;
import escalab.spring.nparrado.backend_spring.repo.IReferenceRepo;
import escalab.spring.nparrado.backend_spring.service.IReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReferenceServiceImpl implements IReferenceService {

    @Autowired
    private IReferenceRepo repo;


    @Override
    public Reference registrar(Reference obj) {
        return repo.save(obj);
    }

    @Override
    public Reference modificar(Reference obj) {
        return repo.save(obj);
    }

    @Override
    public List<Reference> listar() {
        return repo.findAll();
    }

    @Override
    public Reference leerPorId(Integer id) {
        Optional<Reference> op = repo.findById(id);
        return op.orElse(null);
    }

    @Override
    public boolean eliminar(Integer id) {
        repo.deleteById(id);
        return true;
    }
}
