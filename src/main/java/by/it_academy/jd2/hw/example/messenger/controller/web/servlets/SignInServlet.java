package by.it_academy.jd2.hw.example.messenger.controller.web.servlets;

import by.it_academy.jd2.hw.example.messenger.model.User;
import by.it_academy.jd2.hw.example.messenger.view.AuthService;
import by.it_academy.jd2.hw.example.messenger.view.api.IAuthService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "SignInServlet", urlPatterns = "/signIn")
public class SignInServlet extends HttpServlet {

    private final IAuthService authService;

    public SignInServlet() {
        this.authService = AuthService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = authService.authentication(login, password);

        if(user == null){
            req.setAttribute("error", true);
            req.setAttribute("message", "Логин или пароль неверен");
            req.getRequestDispatcher("/views/signIn.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
