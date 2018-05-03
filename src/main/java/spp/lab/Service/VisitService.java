package spp.lab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spp.lab.http.exception.EndOfVisitsException;
import spp.lab.http.exception.ExceptionWithJson;
import spp.lab.http.exception.NoSubscriptionException;
import spp.lab.http.exception.SubscriptionDelayException;
import spp.lab.models.State;
import spp.lab.models.User;
import spp.lab.models.UserSubscription;
import spp.lab.models.Visit;
import spp.lab.reposository.VisitRepository;

import java.util.Date;

@Service
public class VisitService extends BaseService<Visit, VisitRepository> {

    UserSubscriptionService userSubscriptionService;

    @Autowired
    public VisitService(VisitRepository rep, UserSubscriptionService userSubscriptionService) {
        this.repository = rep;
        this.userSubscriptionService = userSubscriptionService;
    }

    public void addVisit(User user) {
        Visit visit = new Visit();
        visit.setUser(user);
        visit.setTrainer(user.getTrainer());
        visit.setState(State.ACTIVE);
        visit.setCreated_at(new Date());
        repository.save(visit);
    }

    private void checkVisits(User user) throws ExceptionWithJson {
        if (user.getSubscription().getAvailable_visits() < 1)
            throw new SubscriptionDelayException();
    }

    private void checkSubscriptionExisting(User user) throws ExceptionWithJson {
        if (user.getSubscription() == null)
            throw new NoSubscriptionException();
    }

    private void checkDate(User user) throws ExceptionWithJson {
        if (user.getSubscription().getEnd_date().before(new Date()))
            throw new EndOfVisitsException();
    }

    private void checkVisitingPossibility(User user) throws ExceptionWithJson {
        this.checkSubscriptionExisting(user);
        this.checkVisits(user);
        this.checkDate(user);
    }

    public String visitGym(User user)
    {
        try {
            checkVisitingPossibility(user);
            UserSubscription userSubscription = userSubscriptionService.findFirstByUser(user).get();
            userSubscription.setAvailable_visits(userSubscription.getAvailable_visits() - 1);
            this.addVisit(user);
            userSubscriptionService.save(userSubscription);
            return " {status : success }";
        } catch (ExceptionWithJson exception)
        {
            if ( user.getSubscription() != null)
                userSubscriptionService.delete(user.getSubscription());
            return exception.getJsonError();
        }
    }

}
