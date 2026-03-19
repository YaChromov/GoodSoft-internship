package org.example.t5s.controller;

import org.example.t5s.model.User;
import org.example.t5s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String deleteUser(@RequestParam("login") String loginToDelete, @SessionAttribute(value = "user", required = false) User currentUser, Model model) {
        userService.deleteUser(loginToDelete, currentUser.getLogin());
        return "redirect:/userlist.jhtml";
    }
}