package com.company.managers;

import com.company.exceptions.NotValidLoginException;
import com.company.interfaces.Login;
import com.company.objects.Friend;
import com.company.objects.User;
import com.company.objects.UserFriend;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class FriendManager extends Manager implements Login {
    boolean loggedOn = false;
    UserFriend friend = new UserFriend();

    public boolean isUserLoggedOn(String username, String password) {
        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, username);
        if (user != null && user.getPassword().equals(password)) {
            return loggedOn = true;
        } else {
            return loggedOn = false;
        }
    }

    public User getUser(String username) {
        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        User user = null;

        try {
            user = session.get(User.class, username);
            if (user.equals(null)) {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }


    public void addFriendToDB(String currentUserName, String password, String friendUserName, String status) {
        try {
            loggedOn = isUserLoggedOn(currentUserName, password);
            if (loggedOn) {
                addFriend(currentUserName, friendUserName, status);
            } else {
                throw new NotValidLoginException("User not logged on");
            }
        } catch (NotValidLoginException e) {
            e.getMessage();
        }

    }

    public void addFriend(String currentUserName, String friendUserName, String status) {
        User currentUser = getUser(currentUserName);
        User friendUser = new UserManager().getUser(friendUserName);
        friend.setCurrentUser(currentUser);
        friend.setFriendUser(friendUser);
        friend.setStatus(status);

        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        //Begins a transaction
        session.beginTransaction();
        //Saves the Customer789 object in the DB
        session.saveOrUpdate(friend);
        // session.save(user);
        //Commits the transaction in the DB
        session.getTransaction().commit();
        //  System.exit(0);
        session.close();


    }

    public ArrayList viewFriend(String currentUser, String password) {

        try {
            loggedOn = isUserLoggedOn(currentUser, password);
            if (loggedOn) {
                return friends(currentUser);
            } else {
                throw new NotValidLoginException("User not logged on");
            }
        } catch (NotValidLoginException e) {
            e.getMessage();
        }
        return null;

    }

    public ArrayList friends(String currentUser){
      // Hashtable<String, Friend> friendHashMap = new Hashtable<>();
    // Hashtable<User,String> friendMap = new Hashtable<>();

        ArrayList<Friend> friendArray= new ArrayList<>();
        String output = "";

        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<com.company.objects.UserFriend> list = session
                .createQuery(
                        "from UserFriend " +
                                "where status like '%Friend Requested%' AND currentUser.userName =(:currentUser)")
                .setParameter("currentUser", currentUser)
                .getResultList();
        list.forEach((UserFriend userFriend) ->
        {
            String friendUserName = userFriend.getFriendUser().getUserName();
            String friendFirstName = userFriend.getFriendUser().getFirstName();
            String friendLastName = userFriend.getFriendUser().getLastName();
            String friendEmail = userFriend.getFriendUser().getEmail();
            String status = userFriend.getStatus();
            Friend friend = new Friend(friendUserName,friendFirstName, friendLastName,friendEmail,status);

            friendArray.add(friend);

        });
        return friendArray;
    }

    public String backup(String currentUser){
        String output;
        ArrayList<String> buildString = new ArrayList<>();
        String start = "<table><thead>" +
                "<tr><th>First Name</th>" +
                "<th>Second Name</th>" +
                "<th>State</th></tr>" +
                "</thead><tbody>";
        String middle = "";

        String end = "</tbody></table>";

        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<com.company.objects.UserFriend> list = session
                .createQuery(
                        "from UserFriend " +
                                "where status like '%Friend Requested%' AND currentUser.userName =(:currentUser)")
                .setParameter("currentUser", currentUser)
                .getResultList();
        list.forEach((UserFriend userFriend) ->
        {
            buildString.add("<tr><td>" + userFriend.getFriendUser().getFirstName() + "</td>" +
                    "<td>" + userFriend.getFriendUser().getLastName() + "</td>" +
                    "<td>" + userFriend.getStatus() + "</td></tr>");
        });
        for (String s : buildString) {
            middle = s + middle;
        }
        output = start + middle + end;

        return output;
    }


}
