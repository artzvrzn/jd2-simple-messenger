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
import java.time.LocalDateTime;

@WebServlet(name = "MessageServlet", urlPatterns = "/message")
public class MessageServlet extends HttpServlet {

    private final IMessageService messageService;

    public MessageServlet() {
        this.messageService = MessageService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/message.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        if(user == null){
            throw new SecurityException("Ошибка безопасности");
        }

        String recipient = req.getParameter("recipient");
        String text = req.getParameter("text");

        Message message = new Message();
        message.setFrom(user.getLogin());
        message.setTo(recipient);
        message.setSendDate(LocalDateTime.now());
        message.setText(text);

        try{
            this.messageService.addMessage(message);
            req.setAttribute("success", true);
        } catch (IllegalArgumentException e){
            req.setAttribute("error", true);
            req.setAttribute("message",  e.getMessage());
        }
        req.getRequestDispatcher("/views/message.jsp").forward(req, resp);
    }
}
