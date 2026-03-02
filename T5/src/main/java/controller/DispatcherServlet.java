package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.SecurityService;
import model.User;
import java.io.IOException;

@WebServlet("*.jhtml")
public class DispatcherServlet extends HttpServlet {

    private final SecurityService securityService = new SecurityService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String contextPath = req.getContextPath();
        String action = req.getParameter("action");
        
        if ("/login.jhtml".equals(path)) {
            handleLogin(req, resp, contextPath, action);
        } else if ("/welcome.jhtml".equals(path)) {
            handleWelcome(req, resp, contextPath);
        } else if ("/loginedit.jhtml".equals(path)) {
            handleEditPassword(req, resp, contextPath, action);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp, String contextPath, String action)
            throws ServletException, IOException {
        if ("POST".equalsIgnoreCase(req.getMethod()) && "login".equals(action)) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");

            User user = securityService.authenticate(login, password);
            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                resp.sendRedirect(contextPath + "/welcome.jhtml");
                return;
            } else {
                req.setAttribute("errorMessage", "Неверный логин или пароль");
            }
        }
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    private void handleWelcome(HttpServletRequest req, HttpServletResponse resp, String contextPath)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
    }

    private void handleEditPassword(HttpServletRequest req, HttpServletResponse resp, String contextPath, String action)
            throws ServletException, IOException {
        if ("POST".equalsIgnoreCase(req.getMethod()) && "changePassword".equals(action)) {
            String oldPassword = req.getParameter("oldPassword");
            String newPassword = req.getParameter("newPassword");
            User user = (User) req.getSession().getAttribute("user");
            if (user != null && securityService.changePassword(user, oldPassword, newPassword)) {
                resp.sendRedirect(contextPath + "/welcome.jhtml?message=passwordChanged");
                return;
            } else {
                req.setAttribute("errorMessage", "Ошибка смены пароля");
            }
        }
        req.getRequestDispatcher("/WEB-INF/jsp/loginedit.jsp").forward(req, resp);
    }
}