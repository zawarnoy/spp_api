package spp.lab.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spp.lab.models.State;
import spp.lab.models.User;
import spp.lab.reposository.UserRepository;

@Service
public class UserService extends BaseService<User, UserRepository>{

    @Autowired
    public UserService(UserRepository rep)
    {
        this.repository = rep;
    }

    public String restoreFromTrash(User user) {
        user.setState(State.ACTIVE);
        repository.save(user);
        return "{ status : success }";
    }

    public String delete(User user)
    {
        user.setState(State.DELETED);
        repository.save(user);
        return "{ status : success }";
    }

    public Iterable<User> findAll() {
        return repository.findAllByState(State.ACTIVE);
    }



}
