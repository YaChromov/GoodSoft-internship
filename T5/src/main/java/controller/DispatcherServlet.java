package controller;

import dto.Request.UserRequest;
import dto.Response.UserResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mapper.UserMapper;
import model.ValidationResult;
import service.UserService;
import model.User;
import validator.UserValidator;

import java.io.IOException;
import java.util.List;

@WebServlet("*.jhtml")
public class DispatcherServlet extends HttpServlet {

    private static final String LOGIN = "/login.jhtml";
    private static final String WELCOME = "/welcome.jhtml";
    private static final String LOGIN_EDIT = "/loginedit.jhtml";
    private static final String USER_LIST = "/userlist.jhtml"; // То, что в строке браузера
    private static final String USER_ADD = "/useradd.jhtml";
    private static final String USER_EDIT = "/useredit.jhtml";

    private static final String LOGIN_PATH = "/WEB-INF/jsp/login.jsp";
    private static final String WELCOME_PATH = "/WEB-INF/jsp/welcome.jsp";
    private static final String LOGIN_EDIT_PATH = "/WEB-INF/jsp/loginedit.jsp";
    private static final String USER_LIST_PATH = "/WEB-INF/jsp/userlist.jsp"; // Где лежит файл
    private static final String USER_ADD_PATH = "/WEB-INF/jsp/useradd.jsp";
    private static final String USER_EDIT_PATH = "/WEB-INF/jsp/useredit.jsp";


    private final UserService userService = new UserService();
    private final UserMapper userMapper = new UserMapper();
    private final UserValidator userValidator = new UserValidator();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String contextPath = req.getContextPath();
        String action = req.getParameter("action");

        if (LOGIN.equals(path)) {
            handleLogin(req, resp, contextPath, action);
        } else if (WELCOME.equals(path)) {
            handleWelcome(req, resp, contextPath);
        } else if (USER_LIST.equals(path)) {
            handleUserList(req, resp, contextPath);
        } else if (USER_ADD.equals(path)) {
            handleUserAdd(req, resp, contextPath);
        } else if(USER_EDIT.equals(path)) {
            handleUserEdit(req, resp, contextPath);
        } else if (LOGIN_EDIT.equals(path)) {
            handleEditPassword(req, resp, contextPath, action);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp, String contextPath, String action)
            throws ServletException, IOException {

        if ("POST".equalsIgnoreCase(req.getMethod()) && "logout".equals(action)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            resp.sendRedirect(contextPath + LOGIN);
            return;
        }

        if ("POST".equalsIgnoreCase(req.getMethod()) && "login".equals(action)) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");

            User user = userService.authenticate(login, password);
            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                resp.sendRedirect(contextPath + WELCOME);
                return;
            } else {
                req.setAttribute("errorMessage", "Неверный логин или пароль");
            }
        }
        req.getRequestDispatcher(LOGIN_PATH).forward(req, resp);
    }

    private void handleWelcome(HttpServletRequest req, HttpServletResponse resp, String contextPath)
            throws ServletException, IOException {
        req.getRequestDispatcher(WELCOME_PATH).forward(req, resp);
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
                    userService.updateUser(userRequest);
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

    private void handleUserList(HttpServletRequest req, HttpServletResponse resp, String contextPath)
            throws ServletException, IOException {

        String loginToDelete = req.getParameter("login");

        if (loginToDelete != null && !loginToDelete.isEmpty()) {
            User currentUser = (User) req.getSession().getAttribute("user");

            if (currentUser != null && currentUser.getLogin().equals(loginToDelete)) {
                req.setAttribute("errorMessage", "Вы не можете удалить свою учетную запись!");
            } else {
                userService.deleteUser(loginToDelete);
                resp.sendRedirect(contextPath + USER_LIST);
                return;
            }
        }

        List<UserResponse> users = userService.getUserList();
        req.setAttribute("userList", users);

        req.getRequestDispatcher(USER_LIST_PATH).forward(req, resp);
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

    private void handleEditPassword(HttpServletRequest req, HttpServletResponse resp, String contextPath, String action)
            throws ServletException, IOException {
        if ("POST".equalsIgnoreCase(req.getMethod()) && "changePassword".equals(action)) {
            String oldPassword = req.getParameter("oldPassword");
            String newPassword = req.getParameter("newPassword");
            User user = (User) req.getSession().getAttribute("user");
            if (user != null && userService.changePassword(user.getLogin(), oldPassword, newPassword)) {
                resp.sendRedirect(contextPath + "/welcome.jhtml?message=passwordChanged");
                return;
            } else {
                req.setAttribute("errorMessage", "Ошибка смены пароля");
            }
        }
        req.getRequestDispatcher(LOGIN_EDIT_PATH).forward(req, resp);
    }
}