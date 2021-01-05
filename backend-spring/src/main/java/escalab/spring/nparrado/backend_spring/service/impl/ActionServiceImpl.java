package escalab.spring.nparrado.backend_spring.service.impl;

import escalab.spring.nparrado.backend_spring.model.Action;
import escalab.spring.nparrado.backend_spring.repo.IActionRepo;
import escalab.spring.nparrado.backend_spring.service.IActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActionServiceImpl implements IActionService {

    @Autowired
    private IActionRepo repo;


    @Override
    public Action registrar(Action obj) {
        return repo.save(obj);
    }

    @Override
    public Action modificar(Action obj) {
        return repo.save(obj);
    }

    @Override
    public List<Action> listar() {
        return repo.findAll();
    }

    @Override
    public Action leerPorId(Integer id) {
        Optional<Action> op = repo.findById(id);
        return op.orElse(null);
    }

    @Override
    public boolean eliminar(Integer id) {
        repo.deleteById(id);
        return true;
    }
}
