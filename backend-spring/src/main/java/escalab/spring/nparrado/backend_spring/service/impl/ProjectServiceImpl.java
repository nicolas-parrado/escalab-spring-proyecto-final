package escalab.spring.nparrado.backend_spring.service.impl;

import escalab.spring.nparrado.backend_spring.model.Project;
import escalab.spring.nparrado.backend_spring.repo.IProjectRepo;
import escalab.spring.nparrado.backend_spring.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private IProjectRepo repo;

    @Override
    public Project registrar(Project obj) {
        return repo.save(obj);
    }

    @Override
    public Project modificar(Project obj) {
        return repo.save(obj);
    }

    @Override
    public List<Project> listar() {
        return repo.findAll();
    }

    @Override
    public Project leerPorId(Integer id) {
        Optional<Project> op = repo.findById(id);
        return op.orElse(null);
    }

    @Override
    public boolean eliminar(Integer id) {
        repo.deleteById(id);
        return true;
    }

}
