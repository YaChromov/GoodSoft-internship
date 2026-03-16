package org.example.t5s.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/welcome.jhtml")
public class WelcomeController extends HttpServlet {
    private static final String WELCOME_PATH = "/WEB-INF/jsp/welcome.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleWelcome(req, resp, req.getContextPath());
    }

    private void handleWelcome(HttpServletRequest req, HttpServletResponse resp, String contextPath)
            throws ServletException, IOException {
        req.getRequestDispatcher(WELCOME_PATH).forward(req, resp);
    }
}