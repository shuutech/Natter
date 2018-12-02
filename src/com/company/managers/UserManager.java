package com.company.managers;

import com.company.exceptions.NotValidLoginException;
import com.company.exceptions.NotValidUserException;
import com.company.interfaces.Login;
import com.company.objects.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserManager extends Manager implements Login {
    public boolean isUserLoggedOn(String username, String password) throws NotValidLoginException {
        boolean loggedOn = false;
        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();


        try {
            session.get(User.class, username);}
        catch (Exception e)
        { throw new NotValidLoginException("You must log in first!");
        }
        User user = session.get(User.class, username);
            if (user != null && user.getPassword().equals(password)) {
                loggedOn = true;
            }

            else if (password == null) {
                loggedOn = false;
                throw new NotValidLoginException("Username or password is incorrect");
            }
            else {
                loggedOn = false;
                throw new NotValidLoginException("Username or password is incorrect");

            }
        session.close();
        return loggedOn;
    }

    public User getUser(String username) throws NotValidUserException  {
        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        User user = new User();
        try {
            user = session.get(User.class, username);
        } catch (Exception h) {
            user=null;
        }
           if (user==null){
               throw new NotValidUserException(username + " does not exist");
           }
session.close();
        return user;
    }


    public void addUserToDB(String username, String password, String firstName, String lastName, String email, String phoneNumber,String location) throws Exception{
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setUserLocation(location);

        SessionFactory sessionFactory = super.getSessionFactory();
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