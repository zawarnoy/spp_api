package spp.lab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spp.lab.models.Payment;
import spp.lab.models.Subscription;
import spp.lab.models.User;
import spp.lab.reposository.PaymentRepository;
import spp.lab.reposository.SubscriptionRepository;
import spp.lab.reposository.UserRepository;
import spp.lab.models.State;

import java.util.Date;

@Service
public class PaymentService extends BaseService<Payment, PaymentRepository> {

    private SubscriptionRepository subscriptionRepository;

    private UserRepository userRepository;

    @Autowired
    public PaymentService(PaymentRepository rep, SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.repository = rep;
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }


    public void create(Subscription subscription, User user) {
        Payment payment = new Payment();
        payment.setPrice(subscription.getPrice());
        payment.setCreated_at(new Date());
        payment.setSubscription(subscription);
        payment.setUser(user);
        payment.setState(State.ACTIVE);

        repository.save(payment);
    }
}

