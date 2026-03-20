package org.example.t5s.controller;

import jakarta.validation.Valid;
import org.example.t5s.dto.Request.UserRequest;

import org.example.t5s.dto.Response.UserResponse;
import org.example.t5s.service.RoleService;
import org.example.t5s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
public class UserEditController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserEditController(UserService userService,RoleService roleService) {
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
    public String processAdd(@Valid @ModelAttribute("user") UserRequest userRequest, BindingResult bindingResult) {

        if (userRequest.getPassword() == null || userRequest.getPassword().trim().isEmpty()) {
            bindingResult.rejectValue   ("password", "field.required", "Пароль обязателен при создании");
        }

        if (bindingResult.hasErrors()) {
            return "useradd";
        }
        userService.addUser(userRequest);

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
    public String processEdit(@Valid @ModelAttribute("user") UserRequest userRequest, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "useredit";
        }
        String currentUserLogin = (principal != null) ? principal.getName() : null;

        userService.updateUserData(userRequest, currentUserLogin);

        return "redirect:/userlist.jhtml";
    }

    @ModelAttribute("allRoles")
    public List<String> populateRoles() {
        return roleService.getAllRoleNames();
    }
}