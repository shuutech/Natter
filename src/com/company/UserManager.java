package com.company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserManager {
    public boolean isUserLoggedOn(String username, String password) {
        SessionFactory sessionFactory = DBManager.getSessionFactory();
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String username) throws Exception {
        SessionFactory sessionFactory = DBManager.getSessionFactory();
        Session session = sessionFactory.openSession();

        User user = session.get(User.class, username);
        if (user.equals(null)){
            throw new Exception();
        }

        return user;
    }

    User user = new User();
    public void addUserToDB(String username, String password, String firstName, String lastName, String email, String phoneNumber,String location) throws Exception{
        user.setUserName(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setUserLocation(location);

        SessionFactory sessionFactory = DBManager.getSessionFactory();
        Session session = sessionFactory.openSession();
        //Begins a transaction
        session.beginTransaction();
        //Saves the Customer789 object in the DB
        session.saveOrUpdate(user);
        // session.save(user);
        //Commits the transaction in the DB
        session.getTransaction().commit();
        //  System.exit(0);
        session.close();
    }
}