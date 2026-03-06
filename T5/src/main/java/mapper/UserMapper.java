package mapper;

import dto.Request.UserRequest;
import dto.Response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import model.User;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

public class UserMapper {

    public User toEntity(UserRequest dto) {
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
        user.setRole(dto.getRole());

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
                IO.println(e);
            }
        }


        String roleStr = req.getParameter("role");
        if (roleStr != null && !roleStr.isBlank()) {
            try {
                userRequest.setRole(User.Role.valueOf(roleStr));
            } catch (IllegalArgumentException e) {
                IO.println(e);
            }
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
        response.setRole(user.getRole());

        return response;
    }

    public List<UserResponse> toResponseList(List<User> users) {
        if (users == null) return Collections.emptyList();

        return users.stream()
                .map(this::toResponse)
                .toList();
    }
}