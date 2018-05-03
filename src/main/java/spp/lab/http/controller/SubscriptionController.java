package spp.lab.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spp.lab.models.Subscription;
import spp.lab.service.SubscriptionService;

import java.util.Optional;

@CrossOrigin
@Controller
@RequestMapping(path = "/subscriptions")
public class SubscriptionController {


    @Autowired
    private SubscriptionService subscriptionService;


    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    String add(
            @RequestParam String name,
            @RequestParam String price,
            @RequestParam String duration,
            @RequestParam String visit_count) {
        subscriptionService.add(name, price, duration, visit_count);
        return "{ status : success }";
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    String delete(@PathVariable(value = "id") String id) {
        subscriptionService.delete(subscriptionService.findByStringId(id).get());
        return "{status : success}";
    }


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Subscription> getAll() {
        return subscriptionService.findAllActive();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}")
    public @ResponseBody
    String update(@PathVariable(value = "id") String id,
                  @RequestParam(required = false) String name,
                  @RequestParam(required = false) String price,
                  @RequestParam(required = false) String visit_count,
                  @RequestParam(required = false) String duration
    ) {


        subscriptionService.edit(subscriptionService.findByStringId(id).get(), name, price, duration, visit_count);

        return "{status : success}";
    }

    @RequestMapping("/{id}")
    public @ResponseBody
    Optional<Subscription> show(@PathVariable(value = "id") String id) {
        return subscriptionService.findByStringId(id);
    }


}
