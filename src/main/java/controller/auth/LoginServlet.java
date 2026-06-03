package controller.auth;

import entity.User;
import service.AuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("loginUser") != null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String userId = request.getParameter("userId");
            String password = request.getParameter("password");

            System.out.println("[LoginServlet] userId = " + userId);
            System.out.println("[LoginServlet] password = " + password);

            User loginUser = authService.login(userId, password);

            request.getSession().setAttribute("loginUser", loginUser);
            request.getSession().setAttribute("userId", loginUser.getUserId());
            request.getSession().setAttribute("userName", loginUser.getUserName());
            request.getSession().setAttribute("userRole", loginUser.getRole());

            System.out.println("[LoginServlet] login success: " + loginUser.getUserId());
            System.out.println("[LoginServlet] role: " + loginUser.getRole());

            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}