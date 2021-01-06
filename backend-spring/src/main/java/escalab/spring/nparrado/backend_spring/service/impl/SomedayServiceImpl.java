package escalab.spring.nparrado.backend_spring.service.impl;

import escalab.spring.nparrado.backend_spring.model.Someday;
import escalab.spring.nparrado.backend_spring.repo.ISomedayRepo;
import escalab.spring.nparrado.backend_spring.service.ISomedayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SomedayServiceImpl implements ISomedayService {

    @Autowired
    private ISomedayRepo repo;


    @Override
    public Someday registrar(Someday obj) {
        return repo.save(obj);
    }

    @Override
    public Someday modificar(Someday obj) {
        return repo.save(obj);
    }

    @Override
    public List<Someday> listar() {
        return repo.findAll();
    }

    @Override
    public Someday leerPorId(Integer id) {
        Optional<Someday> op = repo.findById(id);
        return op.orElse(null);
    }

    @Override
    public boolean eliminar(Integer id) {
        repo.deleteById(id);
        return true;
    }
}
