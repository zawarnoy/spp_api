package spp.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import spp.lab.models.Payment;
import spp.lab.models.Subscription;
import spp.lab.models.User;
import spp.lab.models.UserSubscription;
import spp.lab.reposository.BaseRepository;
import spp.lab.reposository.UserSubscriptionRepository;
import spp.lab.models.State;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/payments")
public class PaymentController {

    @Autowired
    private BaseRepository<Payment, Long> paymentRepository;

    @Autowired
    private BaseRepository<User, Long> userRepository;

    @Autowired
    private BaseRepository<Subscription, Long> subscriptionRepository;

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    String add(
            @RequestParam String subscription_id,
            @RequestParam String user_id) {

        Subscription subscription = subscriptionRepository.findById(Long.valueOf(subscription_id)).get();
        User user = userRepository.findById(Long.valueOf(user_id)).get();

        Payment payment = new Payment();
        payment.setPrice(subscription.getPrice());
        payment.setCreated_at(new Date());
        payment.setSubscription(subscription);
        payment.setUser(user);

        paymentRepository.save(payment);

        Optional<UserSubscription> userSubscription = userSubscriptionRepository.findFirstByUser(user);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, Math.toIntExact(subscription.getDuration()));
        Date endDate = calendar.getTime();

        Date endDateAfter = null;
        if(userSubscription.isPresent() && userSubscription.get().getEnd_date() != null) {
            calendar.setTime(userSubscription.get().getEnd_date());
            calendar.add(Calendar.DATE, Math.toIntExact(subscription.getDuration()));
            endDateAfter = calendar.getTime();
        }


        if (userSubscription.isPresent()) {

            if( userSubscription.get().getAvailable_visits() != null )
                userSubscription.get().setAvailable_visits(userSubscription.get().getAvailable_visits() + subscription.getVisitCount());
            else {
                userSubscription.get().setAvailable_visits(subscription.getVisitCount());
            }

            if( userSubscription.get().getEnd_date() != null) {
                userSubscription.get().setEnd_date(endDateAfter);
            }
            else {
                userSubscription.get().setEnd_date(endDate);
            }

            userSubscriptionRepository.save(userSubscription.get());
        } else {
            UserSubscription newUserSubscription = new UserSubscription();

            newUserSubscription.setState(State.ACTIVE);
            newUserSubscription.setUser(user);
            newUserSubscription.setAvailable_visits(subscription.getVisitCount());
            newUserSubscription .setEnd_date(endDate);

            userSubscriptionRepository.save(newUserSubscription);
        }


        return "{ status : success }";
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    String delete(@PathVariable(value = "id") String id) {
        Optional<Payment> payment = paymentRepository.findById(Long.valueOf(id));
        if (payment != null) {
            paymentRepository.delete(payment.get());
            return "{ status : success }";
        } else {
            return "{ status : error, value : can't find payment }";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Transactional()
    @RequestMapping(method = RequestMethod.POST, path = "/{id}")
    public @ResponseBody
    String edit(@PathVariable(value = "id") String id,
                @RequestParam String admin_id,
                @RequestParam String subscription_id,
                @RequestParam String user_id) {

        Optional<Payment> payment = paymentRepository.findById(Long.valueOf(id));

        if (payment.isPresent()) {

            if (subscription_id != null) {
                payment.get().setSubscription(subscriptionRepository.findById(Long.valueOf(subscription_id)).get());
                payment.get().setPrice(subscriptionRepository.findById(Long.valueOf(subscription_id)).get().getPrice());
            }

            if (user_id != null)
                payment.get().setUser(userRepository.findById(Long.valueOf(user_id)).get());
            paymentRepository.save(payment.get());
            return "{ status : success }";
        }

        return "{ status : error }";
    }

    @RequestMapping("/{id}")
    public @ResponseBody
    Optional<Payment> show(@PathVariable(value = "id") String id) {
        return paymentRepository.findById(Long.valueOf(id));
    }


}
