package spp.lab.controllers;

import org.springframework.web.bind.annotation.*;
import spp.lab.models.UserSubscription;
import spp.lab.reposository.BaseRepository;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/users_subscriptions")
public class UserSubscriptionController {

    BaseRepository<UserSubscription, Long> userSubscriptionRepository;

    @GetMapping()
    public Iterable<UserSubscription> getAll()
    {
        return userSubscriptionRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Optional<UserSubscription> show(@PathVariable(value = "id") String id)
    {
        return userSubscriptionRepository.findById(Long.valueOf(id));
    }


}
