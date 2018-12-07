package com.company.managers;

import com.company.exceptions.*;
import com.company.interfaces.Login;
import com.company.objects.User;
import com.company.objects.UserFriend;
import com.company.validations.FriendValidations;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserManager extends Manager implements Login {
    public boolean isUserLoggedOn(String username, String password) throws NotValidLoginException {
        boolean loggedOn = false;
        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();


        try {
            session.get(User.class, username);
        } catch (Exception e) {
            throw new NotValidLoginException("You must log in first!");
        }
        User user = session.get(User.class, username);
        if (user != null && user.getPassword().equals(password)) {
            loggedOn = true;
        } else if (password == null) {
            loggedOn = false;
            throw new NotValidLoginException("Username or password is incorrect");
        } else {
            loggedOn = false;
            throw new NotValidLoginException("Username or password is incorrect");

        }
        session.close();
        return loggedOn;
    }

    public User getUser(String username) throws NotValidUserException {
        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        User user = new User();
        try {
            user = session.get(User.class, username);
        } catch (Exception h) {
            user = null;
        }
        if (user == null) {
            throw new NotValidUserException(username + " does not exist");
        }
        session.close();
        return user;
    }


    public void addUserToDB(String username,
                            String password,
                            String firstName,
                            String lastName,
                            String email,
                            String phoneNumber,
                            String location) throws UserExistsException,
            NotValidLocation, NotValidUserName, NotValidFirstName, NotValidLastName, NotValidEmail, NotValidPassword, NotValidPhone {

        try{
            User user = getUser(username);
            throw new UserExistsException("User cannot be added");

        }catch(NotValidUserException e) {

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

    public void delete(String username) throws NotValidUserException {

        deleteUserFriend(username);
        deleteUser(username);
    }

    public void deleteUser(String username) throws NotValidUserException {

        User user = getUser(username);
        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteUserFriend(String username) throws NotValidUserException {

        FriendValidations friendValidations = new FriendValidations();
        if (friendValidations.checkUserExists(username)) {
            Session session = super.openSession();
            List<UserFriend> list = session
                    .createQuery(
                            " from UserFriend " +
                                    "where currentUser.userName =(:currentUser) OR friendUser.userName=(:currentUser) ")
                    .setParameter("currentUser", username)
                    .getResultList();

            for (UserFriend userFriend : list) {

                session.beginTransaction();
                session.delete(userFriend);
                session.getTransaction().commit();

            }
            session.close();
        }
    }
}