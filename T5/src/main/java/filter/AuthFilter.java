package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;
import java.util.List;

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

        if ("/login.jhtml".equals(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jhtml");
            return;
        }

        if (user.getRole() == User.Role.USER) {
            if (!userAllowedPaths.contains(path)) {
                resp.sendRedirect(req.getContextPath() + "/welcome.jhtml");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}