package by.it_academy.jd2.hw.example.messenger.controller.web.listeners;

import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.HibernateEntityFactoryInit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class EntityManagerFactoryListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        HibernateEntityFactoryInit.closeEntityFactory();
    }
}
