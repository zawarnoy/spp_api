package spp.lab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spp.lab.models.Subscription;
import spp.lab.reposository.SubscriptionRepository;
import spp.lab.models.State;

@Service
public class SubscriptionService  extends BaseService<Subscription, SubscriptionRepository>{

    @Autowired
    SubscriptionService(SubscriptionRepository rep)
    {
        this.repository = rep;
    }


    public void add(String name, String price, String duration, String visit_count)
    {
        Subscription subscription = new Subscription();
        subscription.setState(State.ACTIVE);
        subscription.setPrice(Long.valueOf(price));
        subscription.setName(name);
        subscription.setDuration(Long.valueOf(duration));
        subscription.setVisitCount(Long.valueOf(visit_count));
        this.save(subscription);
    }


    public void edit(Subscription subscription, String name, String price, String duration, String visit_count)
    {
        if (name != null)
            subscription.setName(name);

        if (price != null)
            subscription.setPrice(Long.valueOf(price));

        if (duration != null)
            subscription.setDuration(Long.valueOf(duration));

        if (visit_count != null)
            subscription.setVisitCount(Long.valueOf(visit_count));

        repository.save(subscription);
    }


}
