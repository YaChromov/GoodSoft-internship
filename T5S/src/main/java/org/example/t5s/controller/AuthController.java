package org.example.t5s.controller;

import jakarta.servlet.http.HttpSession;

import org.example.t5s.model.LoginForm;
import org.example.t5s.model.PasswordChangeForm;
import org.example.t5s.model.User;
import org.example.t5s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login.jhtml")
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login.jhtml")
    public String handleLogin(@ModelAttribute("loginForm") LoginForm form, HttpSession session, Model model) {
        User user = userService.authenticate(form.getLogin(), form.getPassword());

        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/welcome.jhtml";
        } else {
            model.addAttribute("errorMessage", "Неверный логин или пароль");
            return "login";
        }
    }

    @GetMapping("/loginedit.jhtml")
    public String loginEditPage(Model model) {
        model.addAttribute("passwordForm", new PasswordChangeForm());
        return "loginedit";
    }

    @PostMapping("/loginedit.jhtml")
    public String handleEditPassword(@ModelAttribute("passwordForm") PasswordChangeForm form, @SessionAttribute(value = "user", required = false) User user, Model model) {
        if (user != null && userService.changePassword(user.getLogin(), form.getOldPassword(), form.getNewPassword())) {
            return "redirect:/welcome.jhtml?message=passwordChanged";
        } else {
            model.addAttribute("errorMessage", "Ошибка смены пароля");
            return "loginedit";
        }
    }

    @PostMapping("/logout.jhtml")
    public String handleLogout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login.jhtml";
    }
}