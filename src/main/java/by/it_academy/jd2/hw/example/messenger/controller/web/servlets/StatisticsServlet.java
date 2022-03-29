package by.it_academy.jd2.hw.example.messenger.controller.web.servlets;

import by.it_academy.jd2.hw.example.messenger.storage.db.DBAuditStorage;
import by.it_academy.jd2.hw.example.messenger.storage.api.IAuditStorage;
import by.it_academy.jd2.hw.example.messenger.view.StatisticsService;
import by.it_academy.jd2.hw.example.messenger.view.api.IStatisticsService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "StatisticsServlet", urlPatterns = "/statistics")
public class StatisticsServlet extends HttpServlet {

    private final IStatisticsService statisticsService;
    private final IAuditStorage auditStorage;

    public StatisticsServlet() {
        this.statisticsService = StatisticsService.getInstance();
        this.auditStorage = DBAuditStorage.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        resp.setContentType("text/html; charset=UTF-8");

        req.setAttribute("stats", statisticsService.getStats());
        req.setAttribute("audit", auditStorage.read());


        req.getRequestDispatcher("/views/statistics.jsp").forward(req, resp);
    }
}
