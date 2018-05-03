package spp.lab.http.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import spp.lab.models.Role;
import spp.lab.models.User;
import spp.lab.service.UserService;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public @ResponseBody
    String add(
            @RequestParam("name") String name,
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam(name = "trainer_id", required = false) String trainer_id,
            @RequestParam(name = "role", required = false, defaultValue = "USER") String role) {

        userService.createUser(name, login, password, trainer_id, role);
        return "{ status : success }";
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    String delete(@PathVariable(value = "id") String id) {
        Optional<User> user = userService.findByStringId(id);
        if (userService.checkExistence(Long.valueOf(id))) {
            userService.delete(user.get());
            return "{ status : success }";
        } else {
            return "{ status : error, value : can't find user }";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Iterable<User> getAll() {
        return userService.findAllActive();
    }

    @Transactional
    @PostMapping(path = "/{id}")
    public @ResponseBody
    String edit(@PathVariable(value = "id") String id,
                @RequestParam(required = false) String login,
                @RequestParam(required = false) String name,
                @RequestParam(required = false) String password,
                @RequestParam(required = false) String trainer_id,
                @RequestParam(required = false) String role) {

        Optional<User> user = userService.findByStringId(id);

        if (user.isPresent()) {
            userService.edit(user.get(), name, login, password, trainer_id, role);
            return "{status : success }";
        }

        return "{ status : error }";
    }

    @GetMapping("/{id}")
    public Optional<User> show(@PathVariable(value = "id") String id) {
        return userService.findByStringId(id);
    }

    @PostMapping("/{id}/delete_trainer")
    public String delete_trainer(@PathVariable(value = "id") String id) {
        if (userService.checkExistence(Long.valueOf(id))) {
            userService.deleteTrainer(userService.findByStringId(id).get());
            return "{ status : success }";
        }
        return "{ status : can't find user }";
    }

    @GetMapping("/trainers")
    public Iterable<User> getTrainers() {
        return userService.findTrainers();
    }

    @PostMapping("/{id}/change_trainer")
    public String change_trainer(@PathVariable(value = "id") String id,
                                 @RequestParam(value = "trainer_id") String trainer_id) {
        Optional<User> user = userService.findByStringId(id);
        Optional<User> trainer = userService.findByStringId(trainer_id);

        if (user.isPresent())
            if (trainer.isPresent() && trainer.get().getRole() == Role.TRAINER) {
                userService.changeTrainer(user.get(), trainer.get());
                return "{ status : success }";
            } else
                return "{ status : can't find such a trainer }";
        else
            return "{ status : can't find user }";
    }

}
