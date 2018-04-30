package spp.lab.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import spp.lab.models.SafeDeleteEntity;
import spp.lab.models.State;
import spp.lab.reposository.BaseRepository;

import java.util.Optional;

@Service
@NoRepositoryBean
public abstract class BaseService<T extends SafeDeleteEntity, Rep extends BaseRepository<T, Long>> {

    @Autowired
    protected Rep repository;

    public String restoreFromTrash(T entity) {
            entity.setState(State.ACTIVE);
            return "{ status : success }";
    }

    public String delete(T entity)
    {
            entity.setState(State.DELETED);
            repository.delete(entity);
            repository.save(entity);
            return "{ status : success }";
    }

    public Iterable<T> findAll() {
        return repository.findAll();
    }

}
