package escalab.spring.nparrado.backend_spring.service.impl;

import escalab.spring.nparrado.backend_spring.model.Thought;
import escalab.spring.nparrado.backend_spring.repo.IThoughtRepo;
import escalab.spring.nparrado.backend_spring.service.IThoughtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThoughtServiceImpl implements IThoughtService {

    @Autowired
    private IThoughtRepo repo;


    @Override
    public Thought registrar(Thought obj) {
        return repo.save(obj);
    }

    @Override
    public Thought modificar(Thought obj) {
        return repo.save(obj);
    }

    @Override
    public List<Thought> listar() {
        return repo.findAll();
    }

    @Override
    public Thought leerPorId(Integer id) {
        Optional<Thought> op = repo.findById(id);
        return op.orElse(null);
    }

    @Override
    public boolean eliminar(Integer id) {
        repo.deleteById(id);
        return true;
    }
}
