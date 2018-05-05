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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@SpringBootTest
@Transactional
public class SubscriptionServiceTests {

    @Autowired
    SubscriptionService subscriptionService;

    @Test
    @Rollback
    public void add() {
        subscriptionService.add("Yeeah", "11", "33", "35");
        assertEquals("Yeeah", subscriptionService.repository.findFirstByName("Yeeah").get().getName());
    }

}
