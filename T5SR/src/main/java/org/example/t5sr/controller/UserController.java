package org.example.t5sr.controller;


import jakarta.validation.Valid;
import org.example.t5sr.dto.Request.UserRequest;
import org.example.t5sr.dto.Response.UserResponse;
import org.example.t5sr.service.RoleService;
import org.example.t5sr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getUserList();
    }

    @GetMapping("/{login}")
    public UserResponse getUser(@PathVariable String login) {
        return userService.findUserByLogin(login);
    }


    @GetMapping("/roles")
    public List<String> getRoles() {
        return roleService.getAllRoleNames();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
    }

}