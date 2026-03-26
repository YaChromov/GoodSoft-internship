package org.example.t5sr.mapper;

import jakarta.servlet.http.HttpServletRequest;

import org.example.t5sr.dto.Request.UserRequest;
import org.example.t5sr.dto.Response.UserResponse;
import org.example.t5sr.model.Role;
import org.example.t5sr.model.User;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserRequest dto, Set<Role> roles) {
        if (dto == null) {
            return null;
        }

        User user = new User();

        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setSurname(dto.getSurname());
        user.setName(dto.getName());
        user.setPatronymic(dto.getPatronymic());
        user.setBirthday(dto.getBirthday());
        user.setRoles(roles != null ? roles : new HashSet<>());

        return user;
    }

    public UserRequest mapToRequest(HttpServletRequest req) {
        UserRequest userRequest = new UserRequest();
        userRequest.setLogin(req.getParameter("login"));
        userRequest.setPassword(req.getParameter("password"));
        userRequest.setSurname(req.getParameter("surname"));
        userRequest.setName(req.getParameter("name"));
        userRequest.setPatronymic(req.getParameter("patronymic"));
        userRequest.setEmail(req.getParameter("email"));

        String birthdayStr = req.getParameter("birthday");
        if (birthdayStr != null && !birthdayStr.isBlank()) {
            try {
                userRequest.setBirthday(LocalDate.parse(birthdayStr));
            } catch (DateTimeParseException e) {
                System.out.println(e);
            }
        }
        
        String[] roleNames = req.getParameterValues("roles");
        if (roleNames != null) {
            userRequest.setRoles(Arrays.asList(roleNames));
        }

        return userRequest;
    }


    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();

        response.setLogin(user.getLogin());
        response.setEmail(user.getEmail());
        response.setSurname(user.getSurname());
        response.setName(user.getName());
        response.setPatronymic(user.getPatronymic());
        response.setBirthday(user.getBirthday());
        if (user.getRoles() != null) {
            List<String> rolesList = user.getRoles().stream()
                    .map(Role::getName)
                    .sorted()
                    .collect(Collectors.toList());
            response.setRoles(rolesList);
        }

        return response;
    }

    public List<UserResponse> toResponseList(List<User> users) {
        if (users == null) return Collections.emptyList();

        return users.stream()
                .map(this::toResponse)
                .toList();
    }
}