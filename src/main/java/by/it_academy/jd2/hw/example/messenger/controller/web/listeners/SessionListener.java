package by.it_academy.jd2.hw.example.messenger.controller.web.listeners;

import by.it_academy.jd2.hw.example.messenger.view.StatisticsService;
import by.it_academy.jd2.hw.example.messenger.view.api.IStatisticsService;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    private final IStatisticsService statisticsService;

    public SessionListener() {
        this.statisticsService = StatisticsService.getInstance();
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        this.statisticsService.incSessionCount();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        this.statisticsService.decSessionCount();
    }
}
