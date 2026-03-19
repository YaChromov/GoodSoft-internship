package org.example.t5s.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.t5s.model.Role;
import org.example.t5s.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.Set;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final String ADMIN_ROLE_NAME = "ADMIN";

    private final List<String> userAllowedPaths = List.of(
            "/welcome.jhtml",
            "/loginedit.jhtml",
            "/login.jhtml",
            "/logout.jhtml"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();

        if ("/login.jhtml".equals(path)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jhtml");
            return false;
        }

        Set<Role> roles = user.getRoles();
        boolean isAdmin = roles != null && roles.stream()
                .anyMatch(role -> ADMIN_ROLE_NAME.equalsIgnoreCase(role.getName()));

        if (!isAdmin && !userAllowedPaths.contains(path)) {
            response.sendRedirect(request.getContextPath() + "/welcome.jhtml");
            return false;
        }

        return true;
    }
}