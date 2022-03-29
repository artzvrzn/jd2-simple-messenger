package by.it_academy.jd2.hw.example.messenger.controller.web.servlets;

import by.it_academy.jd2.hw.example.messenger.model.User;
import by.it_academy.jd2.hw.example.messenger.view.UserService;
import by.it_academy.jd2.hw.example.messenger.view.api.IUserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "SignUpServlet", urlPatterns = "/signUp")
public class SignUpServlet extends HttpServlet {

    private final IUserService userService;

    public SignUpServlet() {
        this.userService = UserService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/signUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String fio = req.getParameter("fio");

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setFio(fio);

        try{
            this.userService.signUp(user);
            req.getSession().setAttribute("user", user);
            User us = (User) req.getSession().getAttribute("user");
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (IllegalArgumentException e){
            req.setAttribute("error", true);
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/views/signUp.jsp").forward(req, resp);
        }
    }
}
