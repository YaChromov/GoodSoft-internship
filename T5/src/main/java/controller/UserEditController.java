package controller;

import dto.Request.UserRequest;
import factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapper.UserMapper;
import model.User;
import model.ValidationResult;
import service.UserService;
import validator.UserValidator;

import java.io.IOException;

@WebServlet({"/useradd.jhtml", "/useredit.jhtml"})
public class UserEditController extends HttpServlet {
    private final UserService userService = ServiceFactory.getInstance().getUserService();
    private final UserMapper userMapper = new UserMapper();
    private final UserValidator userValidator = new UserValidator();

    private static final String USER_ADD_PATH = "/WEB-INF/jsp/useradd.jsp";
    private static final String USER_EDIT_PATH = "/WEB-INF/jsp/useredit.jsp";
    private static final String USER_LIST = "/userlist.jhtml";
    private static final String USER_ADD = "/useradd.jhtml";
    private static final String USER_EDIT = "/useredit.jhtml";

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

        String method = req.getMethod();

        if ("GET".equalsIgnoreCase(method)) {
            String loginToEdit = req.getParameter("id");

            if (loginToEdit != null && !loginToEdit.isBlank()) {

                User user = userService.findUserByLogin(loginToEdit);
                req.setAttribute("user", user);
            }

            req.getRequestDispatcher(USER_EDIT_PATH).forward(req, resp);
        } else if ("POST".equalsIgnoreCase(method)) {
            try {
                UserRequest userRequest = userMapper.mapToRequest(req);
                ValidationResult validationResult = userValidator.validate(userRequest);

                if(validationResult.isValid()) {
                    userService.updateUserData(userRequest);
                    resp.sendRedirect(req.getContextPath() + USER_LIST);
                    return;
                }
                else {
                    validationResult.addError("login", "ДАнные не валидны");
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