package spp.lab.repository;

import org.aspectj.weaver.Iterators;
import org.assertj.core.util.Lists;
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

import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    @Rollback
    public void add()
    {
        Iterable<User> users = userRepository.findAll();
        int size = Lists.newArrayList(users).size();
        userRepository.save(new User("nameForTestBlaBla", "vladosik", "123", "312", Role.USER));
        assertEquals(size + 1, Lists.newArrayList(userRepository.findAll()).size());
    }

    @Test
    @Rollback
    public void delete()
    {
        Iterable<User> users = userRepository.findAll();
        int size = Lists.newArrayList(users).size();
        userRepository.save(new User("nameForTestBlaBla", "vladosik", "123", "312", Role.USER));
        ArrayList<User> users1 = Lists.newArrayList(users);
        User last = users1.get(users1.size()-1);
        userRepository.delete(last);
        assertEquals(size, Lists.newArrayList(userRepository.findAll()).size());
    }


    @Test
    @Rollback
    public void findByUsername()
    {
        User user = new User("nameForTestBlaBla", "vladosik", "123", "312", Role.USER);
        userRepository.save(user);
        assertEquals(user.getUsername(), userRepository.findFirstByUsername("nameForTestBlabla").get().getUsername());
    }

    @Test
    @Rollback
    public void findByLogin()
    {
        User user = new User("nameForTestBlaBla", "loginForTestBlaBla", "123", "312", Role.USER);
        userRepository.save(user);
        assertEquals(user.getLogin(), userRepository.findFirstByLogin("loginForTestBlaBla").get().getLogin());
    }


}
