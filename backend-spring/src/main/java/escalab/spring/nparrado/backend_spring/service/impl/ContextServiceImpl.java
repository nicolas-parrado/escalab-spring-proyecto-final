package escalab.spring.nparrado.backend_spring.service.impl;

import escalab.spring.nparrado.backend_spring.model.Context;
import escalab.spring.nparrado.backend_spring.repo.IContextRepo;
import escalab.spring.nparrado.backend_spring.service.IContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContextServiceImpl implements IContextService {

    @Autowired
    private IContextRepo repo;

    @Override
    public Context registrar(Context obj) {
        return repo.save(obj);
    }

    @Override
    public Context modificar(Context obj) {
        return repo.save(obj);
    }

    @Override
    public List<Context> listar() {
        return repo.findAll();
    }

    @Override
    public Context leerPorId(Integer id) {
        Optional<Context> op = repo.findById(id);
        return op.orElse(null);
    }

    @Override
    public boolean eliminar(Integer id) {
        repo.deleteById(id);
        return true;
    }

}
