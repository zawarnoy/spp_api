package spp.lab.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @PostMapping("/registration")
    public String registration() {
        return "registration";
    }
}
