package escalab.spring.nparrado.backend_spring.service.impl;

import escalab.spring.nparrado.backend_spring.model.Topic;
import escalab.spring.nparrado.backend_spring.repo.ITopicRepo;
import escalab.spring.nparrado.backend_spring.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements ITopicService {

    @Autowired
    private ITopicRepo repo;

    @Override
    public Topic registrar(Topic obj) {
        return repo.save(obj);
    }

    @Override
    public Topic modificar(Topic obj) {
        return repo.save(obj);
    }

    @Override
    public List<Topic> listar() {
        return repo.findAll();
    }

    @Override
    public Topic leerPorId(Integer id) {
        Optional<Topic> op = repo.findById(id);
        return op.orElse(null);
    }

    @Override
    public boolean eliminar(Integer id) {
        repo.deleteById(id);
        return true;
    }
}
