package escalab.spring.nparrado.backend_spring.service.impl;

import escalab.spring.nparrado.backend_spring.model.Goal;
import escalab.spring.nparrado.backend_spring.repo.IGoalRepo;
import escalab.spring.nparrado.backend_spring.service.IGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements IGoalService {

    @Autowired
    private IGoalRepo repo;

    @Override
    public Goal registrar(Goal obj) {
        return repo.save(obj);
    }

    @Override
    public Goal modificar(Goal obj) {
        return repo.save(obj);
    }

    @Override
    public List<Goal> listar() {
        return repo.findAll();
    }

    @Override
    public Goal leerPorId(Integer id) {
        Optional<Goal> op = repo.findById(id);
        return op.orElse(null);
    }

    @Override
    public boolean eliminar(Integer id) {
        repo.deleteById(id);
        return true;
    }
}
