package spp.lab.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import spp.lab.models.State;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import spp.lab.Service.UserService;
import spp.lab.models.Role;
import spp.lab.models.User;
import spp.lab.reposository.UserRepository;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private UserService userService = new UserService();

    @Transactional void save(User user){
        userRepository.save(user);
    }

    @PostMapping()
    public @ResponseBody
    String add(
            @RequestParam("name") String name,
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam(name = "trainer_id", required = false) String trainer_id) {

        User n = new User();
        n.setUsername(name);
        n.setLogin(login);
//        n.setPassword(NoOpPasswordEncoder.getInstance().encode(password));
        n.setPassword(password);
        if(trainer_id != null)
            n.setTrainer(userRepository.findById(Long.valueOf(trainer_id)).get());
        n.setApiKey("123");
        n.setRole(Role.USER);
        n.setState(State.ACTIVE);
        userRepository.save(n);
        return "{ status : success }";
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    String delete(@PathVariable(value = "id") String id) {
        Optional<User> user = userRepository.findById(Long.valueOf(id));
        if (user != null) {
            return userService.delete(user.get());
        } else {
            return "{ status : error, value : can't find user }";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    @PostMapping(path = "/{id}")
    public @ResponseBody
    String edit(@PathVariable(value = "id") String id,
                            @RequestParam(required = false) String login,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) String password,
                            @RequestParam(required = false) String trainer_id) {
        Optional<User> user = userRepository.findById(Long.valueOf(id));

        if (user.isPresent()){

            if (login != null)
                user.get().setLogin(login);

            if (name != null)
                user.get().setUsername(name);

            if (password != null)
                user.get().setPassword(password);

            if (trainer_id != null)
                user.get().setTrainer(userRepository.findById(Long.valueOf(trainer_id)).get());

            userRepository.save(user.get());
            return "{status : success }";
        }

        return "{ status : error }";
    }

    @GetMapping("/{id}")
    public
    Optional<User> show(@PathVariable(value = "id") String id) {
        return userRepository.findById(Long.valueOf(id));
    }

}
