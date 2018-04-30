package spp.lab.reposository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spp.lab.models.Visit;

@Repository
public interface VisitRepository extends BaseRepository<Visit, Long>{
}
