package controller;

import factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.UserService;

import java.io.IOException;

@WebServlet({"/login.jhtml", "/logout.jhtml", "/loginedit.jhtml"})
public class AuthController extends HttpServlet {
    private final UserService userService = ServiceFactory.getInstance().getUserService();
    private static final String LOGIN_PATH = "/WEB-INF/jsp/login.jsp";
    private static final String LOGIN_EDIT_PATH = "/WEB-INF/jsp/loginedit.jsp";
    private static final String LOGIN = "/login.jhtml";
    private static final String WELCOME = "/welcome.jhtml";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if (LOGIN.equals(path)) {
            req.getRequestDispatcher(LOGIN_PATH).forward(req, resp);
        } else if ("/loginedit.jhtml".equals(path)) {
            req.getRequestDispatcher(LOGIN_EDIT_PATH).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String contextPath = req.getContextPath();

        if ("/logout.jhtml".equals(path)) {
            handleLogout(req, resp, contextPath);
        } else if (LOGIN.equals(path)) {
            handleLogin(req, resp, contextPath);
        } else if ("/loginedit.jhtml".equals(path)) {
            handleEditPassword(req, resp, contextPath);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp, String contextPath)
            throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = userService.authenticate(login, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect(contextPath + WELCOME);
        } else {
            req.setAttribute("errorMessage", "Неверный логин или пароль");
            req.getRequestDispatcher(LOGIN_PATH).forward(req, resp);
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp, String contextPath)
            throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(contextPath + LOGIN);
    }

    private void handleEditPassword(HttpServletRequest req, HttpServletResponse resp, String contextPath)
            throws ServletException, IOException {

        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        User user = (User) req.getSession().getAttribute("user");

        if (user != null && userService.changePassword(user.getLogin(), oldPassword, newPassword)) {
            resp.sendRedirect(contextPath + "/welcome.jhtml?message=passwordChanged");
        } else {
            req.setAttribute("errorMessage", "Ошибка смены пароля");
            req.getRequestDispatcher(LOGIN_EDIT_PATH).forward(req, resp);
        }
    }
}