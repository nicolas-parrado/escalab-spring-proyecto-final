package escalab.spring.nparrado.backend_spring.service.impl;

import escalab.spring.nparrado.backend_spring.model.Delegate;
import escalab.spring.nparrado.backend_spring.repo.IDelegateRepo;
import escalab.spring.nparrado.backend_spring.service.IDelegateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DelegateServiceImpl implements IDelegateService {

    @Autowired
    private IDelegateRepo repo;


    @Override
    public Delegate registrar(Delegate obj) {
        return repo.save(obj);
    }

    @Override
    public Delegate modificar(Delegate obj) {
        return repo.save(obj);
    }

    @Override
    public List<Delegate> listar() {
        return repo.findAll();
    }

    @Override
    public Delegate leerPorId(Integer id) {
        Optional<Delegate> op = repo.findById(id);
        return op.orElse(null);
    }

    @Override
    public boolean eliminar(Integer id) {
        repo.deleteById(id);
        return true;
    }

}