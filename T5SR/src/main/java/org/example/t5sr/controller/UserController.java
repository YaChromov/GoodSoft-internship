package org.example.t5sr.controller;


import jakarta.validation.Valid;
import org.example.t5sr.dto.Request.PasswordChangeRequest;
import org.example.t5sr.dto.Request.UserRequest;
import org.example.t5sr.dto.Response.UserResponse;
import org.example.t5sr.service.RoleService;
import org.example.t5sr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @PutMapping("/{login}")
    public void updateUser(@Valid @RequestBody UserRequest userRequest, Principal principal) {
        String currentUser = (principal != null) ? principal.getName() : null;
        userService.updateUserData(userRequest, currentUser);
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<Void> deleteUser(@PathVariable String login, Principal principal) {
        if (principal != null) {
            userService.deleteUser(login, principal.getName());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeRequest request, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.changePassword(principal.getName(), request.getOldPassword(), request.getNewPassword());

        return ResponseEntity.ok().build();
    }
}