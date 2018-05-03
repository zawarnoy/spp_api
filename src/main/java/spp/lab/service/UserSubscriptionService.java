package spp.lab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spp.lab.models.User;
import spp.lab.models.UserSubscription;
import spp.lab.reposository.UserSubscriptionRepository;
import spp.lab.models.State;


import java.util.Date;
import java.util.Optional;

@Service
public class UserSubscriptionService extends BaseService<UserSubscription, UserSubscriptionRepository> {

    @Autowired
    public UserSubscriptionService(UserSubscriptionRepository userSubscriptionRepository) {
        this.repository = userSubscriptionRepository;
    }

    public void create(User user, Long available_visits, Date endDate){
        UserSubscription userSubscription = new UserSubscription();

        userSubscription.setUser(user);
        userSubscription.setEnd_date(endDate);
        userSubscription.setAvailable_visits(available_visits);
        userSubscription.setState(State.ACTIVE);
        repository.save(userSubscription);
    }

    public Optional<UserSubscription> findFirstByUser(User user) {
        return repository.findFirstByUser(user);
    }

    public void edit(UserSubscription userSubscription, Long addedVisits, Date endDate)
    {
        userSubscription.setAvailable_visits(userSubscription.getAvailable_visits() + addedVisits);
        userSubscription.setEnd_date(endDate);
        repository.save(userSubscription);
    }

    public void delete(UserSubscription userSubscription){
        repository.delete(userSubscription);
    }
}
