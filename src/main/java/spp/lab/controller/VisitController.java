package spp.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import spp.lab.models.User;
import spp.lab.models.Visit;
import spp.lab.reposository.BaseRepository;

import java.util.Date;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/visits")
public class VisitController {

    @Autowired
    private BaseRepository<Visit, Long> visitRepository;

    @Autowired
    private BaseRepository<User, Long> userRepository;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    String add(
            @RequestParam String user_id) {



        if (userRepository.findById(Long.valueOf(user_id)).isPresent())
        {
            if (userRepository.findById(Long.valueOf(user_id)).get().getSubscription().getAvailable_visits() != null)
            {

            }
        }

        Visit visit = new Visit();
        visit.setUser(userRepository.findById(Long.valueOf(user_id)).get());
        visit.setTrainer(userRepository.findById(Long.valueOf(user_id)).get().getTrainer());
        visit.setCreated_at(new Date());

        visitRepository.save(visit);


        return "{ status : success }";
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    String delete(@PathVariable(value = "id") String id) {
        Optional<Visit> visit = visitRepository.findById(Long.valueOf(id));
        if (visit != null) {
            visitRepository.delete(visit.get());
            return "{ status : success }";
        } else {
            return "{ status : error, value : can't find visit }";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Visit> getAll() {
        return visitRepository.findAll();
    }

    @Transactional()
    @RequestMapping(method = RequestMethod.POST, path = "/{id}")
    public @ResponseBody
    String update(@PathVariable(value = "id") String id,
                  @RequestParam String trainer_id,
                  @RequestParam String user_id) {

        Optional<Visit> visit = visitRepository.findById(Long.valueOf(id));

        if (visit.isPresent()) {

            if (trainer_id != null)
                visit.get().setTrainer(userRepository.findById(Long.valueOf(trainer_id)).get());

            if (user_id != null)
                visit.get().setUser(userRepository.findById(Long.valueOf(user_id)).get());

            return "{ status : success }";
        }

        return "{ status : error }";
    }

    @RequestMapping("/{id}")
    public @ResponseBody
    Optional<Visit> show(@PathVariable(value = "id") String id) {
        return visitRepository.findById(Long.valueOf(id));
    }

}
