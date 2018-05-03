package spp.lab.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spp.lab.models.UserSubscription;
import spp.lab.service.UserSubscriptionService;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/users_subscriptions")
public class UserSubscriptionController {

    @Autowired
    UserSubscriptionService userSubscriptionService;

    @GetMapping()
    public Iterable<UserSubscription> getAll()
    {
        return userSubscriptionService.findAllActive();
    }

    @GetMapping(path = "/{id}")
    public Optional<UserSubscription> show(@PathVariable(value = "id") String id)
    {
        return userSubscriptionService.findByStringId(id);
    }


}
