package spp.lab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spp.lab.models.Role;
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


    public void deleteTrainer(User user)
    {
        user.setTrainer(null);
        repository.save(user);
    }


    public Iterable<User> findTrainers()
    {
        return repository.findAllByRole(Role.TRAINER);
    }


    public void createUser(String name, String login, String password, String trainer_id, String role)
    {
        User user = new User();
        user.setUsername(name);
        user.setLogin(login);
        user.setPassword(password);
        if (trainer_id != null)
            user.setTrainer(this.findByStringId(trainer_id).get());
        user.setApiKey("123");
        user.setRole(Role.valueOf(role));
        user.setState(State.ACTIVE);
        repository.save(user);
    }

    public void edit(User user, String name, String login, String password, String trainer_id, String role)
    {
        if (login != null)
            user.setLogin(login);

        if (name != null)
            user.setUsername(name);

        if (password != null)
            user.setPassword(password);

        if (trainer_id != null)
            user.setTrainer(this.findByStringId(trainer_id).get());

        if (role != null)
            user.setRole(Role.valueOf(role));

        repository.save(user);
    }

    public void changeTrainer(User user, User trainer)
    {
        user.setTrainer(trainer);
        repository.save(user);
    }




}
