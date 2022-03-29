package by.it_academy.jd2.hw.example.messenger.view.api;

import java.util.Map;

public interface IStatisticsService {
    long incSessionCount();
    long decSessionCount();
    long getSessionCount();
    Map<String, Object> getStats();
}
