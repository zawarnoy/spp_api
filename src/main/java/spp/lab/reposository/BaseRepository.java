package spp.lab.reposository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import spp.lab.models.SafeDeleteEntity;
import spp.lab.models.State;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, Long > extends CrudRepository<T ,Long> {

    Iterable<T> findAllByState(State state);

    Optional<T> findOneByIdAndState(Long id, State state);

}
