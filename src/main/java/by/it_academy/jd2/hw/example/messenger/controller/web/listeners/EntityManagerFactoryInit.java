package by.it_academy.jd2.hw.example.messenger.controller.web.listeners;

import by.it_academy.jd2.hw.example.messenger.storage.hibernate.HibernateEntityFactoryInit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class EntityManagerFactoryInit implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        HibernateEntityFactoryInit.closeEntityFactory();
    }
}
