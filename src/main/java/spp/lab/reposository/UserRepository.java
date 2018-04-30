package spp.lab.reposository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spp.lab.models.Role;
import spp.lab.models.User;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    Iterable<User> findAllByRole(Role role);

}
