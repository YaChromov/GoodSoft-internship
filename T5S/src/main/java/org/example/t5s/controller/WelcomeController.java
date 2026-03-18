package org.example.t5s.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/welcome.jhtml")
    public String welcome() {
        return "welcome";
    }
}