package spp.lab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spp.lab.models.Subscription;
import spp.lab.reposository.BaseRepository;

import java.util.Optional;

@CrossOrigin
@Controller
@RequestMapping(path = "/subscriptions")
public class SubscriptionController {


    @Autowired
    private BaseRepository<Subscription, Long  > subscriptionRepository;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    String add(
            @RequestParam String name,
            @RequestParam String price,
            @RequestParam String duration,
            @RequestParam String visit_count) {

        Subscription n = new Subscription();
        n.setName(name);
        n.setPrice(Long.valueOf(price));
        n.setDuration(Long.valueOf(duration));
        n.setVisitCount(Long.valueOf(visit_count));
        subscriptionRepository.save(n);
        return "{ status : success }";
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    String delete(@PathVariable(value = "id") String id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(Long.valueOf(id));
        if (subscription != null) {
            subscriptionRepository.delete(subscription.get());
            return "{ status : success }";
        } else {
            return "{ status : error, value : can't find user }";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Subscription> getAll() {
        return subscriptionRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}")
    public @ResponseBody
    Optional<Subscription> update(@PathVariable(value = "id") String id,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String price,
                                @RequestParam(required = false) String visit_count,
                                @RequestParam(required = false) String duration
    ) {

        Optional<Subscription> subscription = subscriptionRepository.findById(Long.valueOf(id));
        if (name != null)
            subscription.get().setName(name);

        if (price != null)
            subscription.get().setPrice(Long.valueOf(price));

        if (duration != null)
            subscription.get().setDuration(Long.valueOf(duration));

        if (visit_count != null)
            subscription.get().setVisitCount(Long.valueOf(visit_count));

        subscriptionRepository.save(subscription.get());
        return subscription;
    }

    @RequestMapping("/{id}")
    public @ResponseBody
    Optional<Subscription> show(@PathVariable(value = "id") String id) {
        return subscriptionRepository.findById(Long.valueOf(id));
    }


}
