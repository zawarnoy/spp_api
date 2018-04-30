package spp.lab.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spp.lab.models.State;
import spp.lab.models.User;
import spp.lab.reposository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String restoreFromTrash(User user) {
        user.setState(State.ACTIVE);
        userRepository.save(user);
        return "{ status : success }";
    }

    public String delete(User user)
    {
        user.setState(State.DELETED);
        userRepository.save(user);
        return "{ status : success }";
    }

    public Iterable<User> findAll() {
        return userRepository.findAllByState(State.ACTIVE);
    }



}
