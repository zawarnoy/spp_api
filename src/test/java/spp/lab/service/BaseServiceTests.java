package spp.lab.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spp.lab.config.TestConfig;
import spp.lab.reposository.UserRepository;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@SpringBootTest
@Transactional
public class BaseServiceTests {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @Test
    @Rollback
    public void addDaysToGranted() {
        Calendar calendar = Calendar.getInstance();
        Date date = userService.addDaysToGrantedDate(new Date(), (long) 5);
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, Math.toIntExact(5));
        assertEquals(date, calendar.getTime());
    }

    @Test
    @Rollback
    public void addDaysToCurrent() {
        Calendar calendar = Calendar.getInstance();
        Date date = userService.addDaysToCurrentDate((long) 5);
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, Math.toIntExact(5));
        assertEquals(date, calendar.getTime());
    }


    @Test
    @Rollback
    public void checkExistance() {
        Boolean t1 = userRepository.findById((long) 5).isPresent();

        assertEquals(userService.checkExistence((long) 5), t1);
    }
}