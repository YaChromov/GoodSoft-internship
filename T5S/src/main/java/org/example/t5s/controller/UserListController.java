package org.example.t5s.controller;

import org.example.t5s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/userlist.jhtml")
public class UserListController {

    private final UserService userService;

    @Autowired
    public UserListController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUserList(Model model) {
        model.addAttribute("userList", userService.getUserList());
        return "userlist";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("login") String loginToDelete, Principal principal) {
        if (principal != null) {
            userService.deleteUser(loginToDelete, principal.getName());
        }
        return "redirect:/userlist.jhtml";
    }
}