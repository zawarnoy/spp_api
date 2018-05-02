package spp.lab.repository;

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
import spp.lab.models.Subscription;
import spp.lab.models.User;
import spp.lab.reposository.SubscriptionRepository;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@SpringBootTest
@Transactional
public class SubscriptionRepositoryTests {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Test
    @Rollback
    public void add()
    {
        Iterable<Subscription> subscriptions = subscriptionRepository.findAll();
        int size = Lists.newArrayList(subscriptions).size();
        subscriptionRepository.save(new Subscription("TestSubscription", (long)5, (long)5, (long)5));
        assertEquals(size + 1, Lists.newArrayList(subscriptionRepository.findAll()).size());
    }


    @Test
    @Rollback
    public void delete()
    {
        Iterable<Subscription> subscriptions = subscriptionRepository.findAll();
        int size = Lists.newArrayList(subscriptions).size();
        subscriptionRepository.save(new Subscription("TestSubscription", (long)5, (long)5, (long)5));
        ArrayList<Subscription> users1 = Lists.newArrayList(subscriptions);
        Subscription last = users1.get(users1.size()-1);
        subscriptionRepository.delete(last);
        assertEquals(size, Lists.newArrayList(subscriptionRepository.findAll()).size());
    }


    @Test
    @Rollback
    public void findByUsername()
    {
        Subscription subscription = subscriptionRepository.save(new Subscription("TestSubscription", (long)5, (long)5, (long)5));
        subscriptionRepository.save(subscription);
        assertEquals(subscription, subscriptionRepository.findFirstByName("TestSubscription").get().getName());
    }

    @Test
    @Rollback
    public void findByLogin()
    {
//        User user = new User("nameForTestBlaBla", "loginForTestBlaBla", "123", "312", Role.USER);
//        userRepository.save(user);
//        assertEquals(user.getLogin(), userRepository.findFirstByLogin("loginForTestBlaBla").get().getLogin());
    }



}
