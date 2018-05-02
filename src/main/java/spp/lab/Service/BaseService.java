package spp.lab.Service;

import org.springframework.beans.factory.annotation.Autowired;
import spp.lab.models.SafeDeleteEntity;
import spp.lab.models.State;
import spp.lab.reposository.BaseRepository;


public class BaseService<T extends SafeDeleteEntity, Rep extends BaseRepository<T, Long>> {

    @Autowired
    protected Rep repository;


    public String restoreFromTrash(T entity) {
            entity.setState(State.ACTIVE);
            return "{ status : success }";
    }

    public String delete(T entity)
    {
            entity.setState(State.DELETED);
            repository.save(entity);
            return "{ status : success }";
    }

    public Iterable<T> findAll() {
        return repository.findAll();
    }

}
