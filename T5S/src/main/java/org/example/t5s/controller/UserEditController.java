package org.example.t5s.controller;

import org.example.t5s.dto.Request.UserRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.t5s.dto.Response.UserResponse;
import org.example.t5s.mapper.UserMapper;
import org.example.t5s.model.User;
import org.example.t5s.model.ValidationResult;
import org.example.t5s.service.RoleService;
import org.example.t5s.service.UserService;
import org.example.t5s.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


import java.io.IOException;
import java.util.List;

@WebServlet({"/useradd.jhtml", "/useredit.jhtml"})
public class UserEditController extends HttpServlet {
    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    private static final String USER_ADD_PATH = "/WEB-INF/jsp/useradd.jsp";
    private static final String USER_EDIT_PATH = "/WEB-INF/jsp/useredit.jsp";
    private static final String USER_LIST = "/userlist.jhtml";
    private static final String USER_ADD = "/useradd.jhtml";
    private static final String USER_EDIT = "/useredit.jhtml";

    @Autowired
    public UserEditController(@Lazy UserService userService,@Lazy RoleService roleService,@Lazy UserMapper userMapper,@Lazy UserValidator userValidator){
        this.userService = userService;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    @Override
    public void init() throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String contextPath = req.getContextPath();

        if (USER_ADD.equals(path)) {
            handleUserAdd(req, resp, contextPath);
        } else if (USER_EDIT.equals(path)) {
            handleUserEdit(req, resp, contextPath);
        }
    }

    private void handleUserEdit(HttpServletRequest req, HttpServletResponse resp, String contextPath)
            throws ServletException, IOException {

        List<String> allRoleNames = roleService.getAllRoleNames();
        req.setAttribute("allRoles", allRoleNames);
        String method = req.getMethod();

        if ("GET".equalsIgnoreCase(req.getMethod())) {
            String login = req.getParameter("id");
            UserResponse user = userService.findUserByLogin(login);
            req.setAttribute("user", user);

            req.getRequestDispatcher(USER_EDIT_PATH).forward(req, resp);

        } else if ("POST".equalsIgnoreCase(method)) {
            try {
                UserRequest userRequest = userMapper.mapToRequest(req);
                ValidationResult validationResult = userValidator.validate(userRequest);

                if(validationResult.isValid()) {
                    User currentUser = (User) req.getSession().getAttribute("user");
                    String currentUserLogin = (currentUser != null) ? currentUser.getLogin() : null;

                    userService.updateUserData(userRequest, currentUserLogin);

                    resp.sendRedirect(req.getContextPath() + USER_LIST);
                    return;
                }
                else {
                    validationResult.addError("login", "Данные не валидны");
                }
                req.setAttribute("errors", validationResult.getErrors());
                req.setAttribute("user", userRequest);

                req.getRequestDispatcher(USER_EDIT_PATH).forward(req, resp);

            } catch (Exception e) {
                req.setAttribute("error", "Ошибка при обновлении данных: " + e.getMessage());
                req.getRequestDispatcher(USER_EDIT_PATH).forward(req, resp);
            }
        }
    }

    private void handleUserAdd(HttpServletRequest req, HttpServletResponse resp, String contextPath)
            throws ServletException, IOException {

        List<String> allRoleNames = roleService.getAllRoleNames();
        req.setAttribute("allRoles", allRoleNames);

        if ("POST".equalsIgnoreCase(req.getMethod())) {
            UserRequest userRequest = userMapper.mapToRequest(req);
            ValidationResult validationResult = userValidator.validate(userRequest);

            if (validationResult.isValid()) {
                boolean isAdded = userService.addUser(userRequest);
                if (isAdded) {
                    resp.sendRedirect(contextPath + USER_LIST);
                    return;
                } else {
                    req.setAttribute("errorMessage", "Пользователь с таким логином уже существует!");
                }
            } else {
                req.setAttribute("errorMessage", "Пожалуйста, исправьте ошибки в форме");
                req.setAttribute("errors", validationResult.getErrors());
            }
            req.setAttribute("user", userRequest);
        }

        req.getRequestDispatcher(USER_ADD_PATH).forward(req, resp);
    }
}