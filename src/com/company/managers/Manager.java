package com.company.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Manager {
    private static SessionFactory sessionFactory;

    private static void setupSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        if(sessionFactory == null)
            setupSessionFactory();
        return sessionFactory;
    }

    public Session openSession(){
        Session session = getSessionFactory().openSession();
        return session;
    }

    public void beginTransaction(){
        openSession().beginTransaction();
    }

    public void saveOrUpdate(Object obj){
        Session session = openSession();
        session.beginTransaction();
        session.saveOrUpdate(obj);
        session.getTransaction().commit();
        session.close();
    }

    public void update(Session session, Object obj){
        session.beginTransaction();
        session.update(obj);
        session.getTransaction().commit();
    }








}