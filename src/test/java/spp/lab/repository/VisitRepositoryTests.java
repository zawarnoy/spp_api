package spp.lab.repository;

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
import spp.lab.models.Visit;
import spp.lab.reposository.VisitRepository;
import spp.lab.service.VisitService;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@SpringBootTest
@Transactional
public class VisitRepositoryTests {


    @Autowired
    VisitRepository visitRepository;

    @Autowired
    VisitService visitService;

    @Test
    @Rollback
    public void add() {

    }



}
