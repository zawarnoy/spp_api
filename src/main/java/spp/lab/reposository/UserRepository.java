package spp.lab.reposository;

import org.springframework.stereotype.Repository;
import spp.lab.models.Role;
import spp.lab.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    Iterable<User> findAllByRole(Role role);

    Optional<User> findFirstByUsername(String username);

    Optional<User> findFirstByLogin(String login);

}
