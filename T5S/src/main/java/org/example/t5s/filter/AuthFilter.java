package org.example.t5s.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.t5s.model.Role;
import org.example.t5s.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebFilter("*.jhtml")
public class AuthFilter implements Filter {

    private final List<String> userAllowedPaths = List.of(
            "/welcome.jhtml",
            "/loginedit.jhtml",
            "/login.jhtml",
            "/logout.jhtml"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getServletPath();

        //Страница логина доступна всем
        if ("/login.jhtml".equals(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        //Если не залогинен — на вход
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jhtml");
            return;
        }

        //Логика проверки множественных ролей
        Set<Role> roles = user.getRoles();

        // Проверяем, есть ли у пользователя права администратора
        boolean isAdmin = roles.stream()
                .anyMatch(role -> "ADMIN".equalsIgnoreCase(role.getName()));

        // Если НЕ админ, проверяем доступ к путям для обычного юзера
        if (!isAdmin) {
            if (!userAllowedPaths.contains(path)) {
                // Если путь не в "белом списке", отправляем на главную
                resp.sendRedirect(req.getContextPath() + "/welcome.jhtml");
                return;
            }
        }

        // Если админ или путь разрешен — идем дальше
        chain.doFilter(request, response);
    }
}