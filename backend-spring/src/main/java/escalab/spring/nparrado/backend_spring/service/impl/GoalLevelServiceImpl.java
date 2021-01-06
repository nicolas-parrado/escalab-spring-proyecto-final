package escalab.spring.nparrado.backend_spring.service.impl;

import escalab.spring.nparrado.backend_spring.model.GoalLevel;
import escalab.spring.nparrado.backend_spring.repo.IGoalLevelRepo;
import escalab.spring.nparrado.backend_spring.service.IGoalLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalLevelServiceImpl implements IGoalLevelService {

    @Autowired
    private IGoalLevelRepo repo;


    @Override
    public GoalLevel registrar(GoalLevel obj) {
        return repo.save(obj);
    }

    @Override
    public GoalLevel modificar(GoalLevel obj) {
        return repo.save(obj);
    }

    @Override
    public List<GoalLevel> listar() {
        return repo.findAll();
    }

    @Override
    public GoalLevel leerPorId(Integer id) {
        Optional<GoalLevel> op = repo.findById(id);
        return op.orElse(null);
    }

    @Override
    public boolean eliminar(Integer id) {
        repo.deleteById(id);
        return true;
    }
}
