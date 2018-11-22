package com.company;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBManager {
    private static SessionFactory sessionFactory;

    private static void setupSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null)
            setupSessionFactory();
        return sessionFactory;
    }
}