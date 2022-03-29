package by.it_academy.jd2.hw.example.messenger.view;

import by.it_academy.jd2.hw.example.messenger.view.api.IMessageService;
import by.it_academy.jd2.hw.example.messenger.view.api.IStatisticsService;
import by.it_academy.jd2.hw.example.messenger.view.api.IUserService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class StatisticsService implements IStatisticsService {
    private final static StatisticsService instance = new StatisticsService();

    private final AtomicLong sessionCounter;
    private final IUserService userService;
    private final IMessageService messageService;

    private StatisticsService() {
        this.sessionCounter = new AtomicLong(0);
        this.userService = UserService.getInstance();
        this.messageService = MessageService.getInstance();
    }

    @Override
    public Map<String, Object> getStats() {
        Map<String, Object> stats  = new HashMap<>();
        stats.put("Количество Пользователей", this.userService.getCount());
        stats.put("Количество Сообщений", this.messageService.getCount());
        stats.put("Количество Активных сессий", this.getSessionCount());
        return stats;
    }

    @Override
    public long incSessionCount() {
        return this.sessionCounter.incrementAndGet();
    }

    @Override
    public long decSessionCount() {
        return this.sessionCounter.decrementAndGet();
    }

    @Override
    public long getSessionCount() {
        return this.sessionCounter.get();
    }

    public static StatisticsService getInstance() {
        return instance;
    }
}
