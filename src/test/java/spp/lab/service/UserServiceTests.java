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
    public void softDelete()
    {
        User user = new User("testUserForSoftDelete", "test", "test", "test",  Role.USER);
        userRepository.save(user);
        userService.delete(user);
        Optional<User> deletedUser = userRepository.findFirstByUsername("testUserForSoftDelete");
        assertEquals(State.DELETED, deletedUser.get().getState());
    }

}
