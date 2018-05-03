package spp.lab.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spp.lab.models.Visit;
import spp.lab.service.UserService;
import spp.lab.service.VisitService;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/visits")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    String add(
            @RequestParam String user_id) {
        return visitService.visitGym(userService.findByStringId(user_id).get());
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    String delete(@PathVariable(value = "id") String id) {
        visitService.delete(visitService.findByStringId(id).get());
        return "{status : success}";
    }


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Visit> getAll() {
        return visitService.findAllActive();
    }

//    @Transactional()
//    @RequestMapping(method = RequestMethod.POST, path = "/{id}")
//    public @ResponseBody
//    String update(@PathVariable(value = "id") String id,
//                  @RequestParam String user_id) {
//
//        Optional<Visit> visit = visitRepository.findById(Long.valueOf(id));
//
//        if (visit.isPresent()) {
//
//            if (trainer_id != null)
//                visit.get().setTrainer(userRepository.findById(Long.valueOf(trainer_id)).get());
//
//            if (user_id != null)
//                visit.get().setUser(userRepository.findById(Long.valueOf(user_id)).get());
//
//            return "{ status : success }";
//        }
//
//        return "{ status : error }";
//    }

    @RequestMapping("/{id}")
    public @ResponseBody
    Optional<Visit> show(@PathVariable(value = "id") String id) {
        return visitService.findByStringId(id);
    }

}
