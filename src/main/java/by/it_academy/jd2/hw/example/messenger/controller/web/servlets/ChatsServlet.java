package by.it_academy.jd2.hw.example.messenger.controller.web.servlets;

import by.it_academy.jd2.hw.example.messenger.model.Message;
import by.it_academy.jd2.hw.example.messenger.model.User;
import by.it_academy.jd2.hw.example.messenger.view.MessageService;
import by.it_academy.jd2.hw.example.messenger.view.api.IMessageService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ChatsServlet", urlPatterns = "/chats")
public class ChatsServlet extends HttpServlet {

    private final IMessageService messageService;

    public ChatsServlet() {
        this.messageService = MessageService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        if(user == null){
            throw new SecurityException("Ошибка безопасности");
        }

        List<Message> messages = this.messageService.get(user);

        req.setAttribute("chat", messages);
        req.getRequestDispatcher("/views/chats.jsp").forward(req, resp);
    }
}
