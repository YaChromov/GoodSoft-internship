package org.example.t5s.controller;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.t5s.dto.Response.UserResponse;
import org.example.t5s.model.User;
import org.example.t5s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


import java.io.IOException;
import java.util.List;

@WebServlet("/userlist.jhtml")
public class UserListController extends HttpServlet {
    private final UserService userService;
    private static final String USER_LIST_PATH = "/WEB-INF/jsp/userlist.jsp";
    private static final String USER_LIST = "/userlist.jhtml";


    @Autowired
    public UserListController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init() throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleUserList(req, resp, req.getContextPath());
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
}