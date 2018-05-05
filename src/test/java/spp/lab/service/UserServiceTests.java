package spp.lab.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spp.lab.config.TestConfig;
import spp.lab.models.Role;
import spp.lab.models.User;
import spp.lab.reposository.UserRepository;
import spp.lab.models.State;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @Rollback
    public void softDelete() {
        User user = new User("testUserForSoftDelete", "test", "test", "test", Role.USER);
        userRepository.save(user);
        userService.delete(user);
        Optional<User> deletedUser = userRepository.findFirstByUsername("testUserForSoftDelete");
        assertEquals(State.DELETED, deletedUser.get().getState());
    }

    @Test
    @Rollback
    public void changeTrainer() {
        User trainer = new User("Trainer", "test", "test", "test", Role.TRAINER);
        userRepository.save(trainer);

        User trainer2 = new User("Trainer2", "test", "test", "test", Role.TRAINER);
        userRepository.save(trainer2);

        User user = new User("testUserForTrainer", "test", "test", "test", Role.USER, trainer);
        userRepository.save(user);

        userService.changeTrainer(user, trainer2);
        assertEquals(trainer2, user.getTrainer());
    }

    @Test
    @Rollback
    public void deleteTrainer() {

        User trainer = new User("Trainer", "test", "test", "test", Role.TRAINER);
        userRepository.save(trainer);

        User user = new User("testUserForTrainerDeleting", "test", "test", "test", Role.USER, trainer);
        userRepository.save(user);

        userService.deleteTrainer(user);

        assertEquals(null, userRepository.findFirstByUsername("testUserForTrainerDeleting").get().getTrainer());
    }


    @Test
    @Rollback
    public void saveTest() {
        User user = new User("testUserForCreating", "test", "test", "test", Role.USER);
        userService.save(user);

        assertEquals(user, userRepository.findFirstByUsername("testUserForCreating").get());
    }

    @Test
    @Rollback
    public void findTrainers()
    {
        userRepository.deleteAll();
        User user = new User("testUserForCreating", "test", "test", "test", Role.USER);
        userService.save(user);

        User trainer1 = new User("testUserForCreating", "test", "test", "test", Role.TRAINER);
        userService.save(trainer1);
        User trainer2 = new User("testUserForCreating", "test", "test", "test", Role.TRAINER);
        userService.save(trainer2);

        assertEquals(2, ArrayList.class.cast(userService.findTrainers()).size());
    }

}
