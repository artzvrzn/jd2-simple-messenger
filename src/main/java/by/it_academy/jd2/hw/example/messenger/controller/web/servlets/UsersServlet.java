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
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@WebServlet(name = "UsersServlet", urlPatterns = "/admin/users")
public class UsersServlet extends HttpServlet {

    private final IUserService userService;

    public UsersServlet() {
        this.userService = UserService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        resp.setContentType("text/html; charset=UTF-8");
        Collection<User> users = userService.getAll();
        for (User user : users) {
            writer.println(user);
        }
    }
}
