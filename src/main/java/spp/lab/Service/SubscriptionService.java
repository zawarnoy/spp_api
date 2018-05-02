package spp.lab.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spp.lab.models.Subscription;
import spp.lab.reposository.SubscriptionRepository;

@Service
public class SubscriptionService  extends BaseService<Subscription, SubscriptionRepository>{

    @Autowired
    SubscriptionService(SubscriptionRepository rep)
    {
        this.repository = rep;
    }

}
