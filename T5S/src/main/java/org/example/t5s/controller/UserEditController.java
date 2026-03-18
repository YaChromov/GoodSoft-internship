package org.example.t5s.controller;

import jakarta.validation.Valid;
import org.example.t5s.dto.Request.UserRequest;

import org.example.t5s.dto.Response.UserResponse;
import org.example.t5s.model.User;
import org.example.t5s.service.RoleService;
import org.example.t5s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserEditController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserEditController(@Lazy UserService userService, @Lazy RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/useradd.jhtml")
    public String showAddForm(Model model) {
        model.addAttribute("user", new UserRequest());
        model.addAttribute("allRoles", roleService.getAllRoleNames());
        return "useradd";
    }

    @PostMapping("/useradd.jhtml")
    public String processAdd(@Valid @ModelAttribute("user") UserRequest userRequest, BindingResult bindingResult, Model model) {

        if (userRequest.getPassword() == null || userRequest.getPassword().trim().isEmpty()) {
            bindingResult.rejectValue("password", "error.user", "Пароль обязателен при создании");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoleNames());
            return "useradd";
        }

        if (!userService.addUser(userRequest)) {
            model.addAttribute("error", "Пользователь с таким логином уже существует!"); // поменял на "error" для JSP
            model.addAttribute("allRoles", roleService.getAllRoleNames());
            return "useradd";
        }

        return "redirect:/userlist.jhtml";
    }

    @GetMapping("/useredit.jhtml")
    public String showEditForm(@RequestParam("id") String login, Model model) {
        UserResponse user = userService.findUserByLogin(login);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoleNames());
        return "useredit";
    }

    @PostMapping("/useredit.jhtml")
    public String processEdit(@Valid @ModelAttribute("user") UserRequest userRequest, BindingResult bindingResult, @SessionAttribute(value = "user", required = false) User currentUser, Model model) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);

            model.addAttribute("allRoles", roleService.getAllRoleNames());
            return "useredit";
        }

        try {
            String currentUserLogin = (currentUser != null) ? currentUser.getLogin() : null;
            userService.updateUserData(userRequest, currentUserLogin);
            return "redirect:/userlist.jhtml";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при обновлении: " + e.getMessage());
            model.addAttribute("allRoles", roleService.getAllRoleNames());
            return "useredit";
        }
    }
}