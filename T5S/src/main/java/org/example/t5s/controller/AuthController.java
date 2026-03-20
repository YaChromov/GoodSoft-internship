package org.example.t5s.controller;

import org.example.t5s.model.LoginForm;
import org.example.t5s.model.PasswordChangeForm;
import org.example.t5s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login.jhtml")
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @GetMapping("/loginedit.jhtml")
    public String loginEditPage(Model model) {
        model.addAttribute("passwordForm", new PasswordChangeForm());
        return "loginedit";
    }


    @PostMapping("/loginedit.jhtml")
    public String handleEditPassword(@ModelAttribute("passwordForm") PasswordChangeForm form, Principal principal) {
        if (principal == null) {
            return "redirect:/login.jhtml";
        }
        userService.changePassword(principal.getName(), form.getOldPassword(), form.getNewPassword());
        return "redirect:/welcome.jhtml?message=passwordChanged";
    }

}