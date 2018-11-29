package com.company.managers;

import com.company.enums.FriendStatus;
import com.company.exceptions.NotValidLoginException;
import com.company.exceptions.NotValidUserException;
import com.company.interfaces.Login;
import com.company.objects.Friend;
import com.company.objects.User;
import com.company.objects.UserFriend;
import com.company.validations.FriendValidations;
import org.hibernate.*;

import java.util.*;

public class FriendManager extends Manager implements Login {

    public boolean isUserLoggedOn(String username, String password) throws NotValidLoginException {
        boolean loggedOn = new UserManager().isUserLoggedOn(username, password);
        return loggedOn;
    }

    public User getUser(String username) throws NotValidUserException {
        User user = new UserManager().getUser(username);
        return user;
    }


    public void addOrUpdateFriend(String currentUserName, String password, String friendUserName) throws NotValidLoginException, NotValidUserException {

        boolean loggedOn = false;
        FriendValidations friendValidations = new FriendValidations();
        loggedOn = isUserLoggedOn(currentUserName, password);
        String status;
        if (loggedOn) {
            if (friendValidations.getFriendRecord(currentUserName, friendUserName) == null) {
                createNewFriendRecord(currentUserName, friendUserName);
            } else {
                ArrayList<Friend> friend = friendValidations.getFriendRecord(currentUserName, friendUserName);
                for (Friend f : friend) {
                    if (currentUserName.equals(f.getCurrentUserName())) {
                        status = friendValidations.updateUserRecord(f.getCurrentUserName(), f.getFriendUserName());
                        updateFriendStatus(f.getCurrentUserName(), f.getFriendUserName(), status);
                    } else if (!currentUserName.equals(f.getCurrentUserName())) {
                        status = friendValidations.updateFriendRecord(f.getCurrentUserName(), f.getFriendUserName());
                        updateFriendStatus(f.getCurrentUserName(), f.getFriendUserName(), status);
                    }
                }

            }


        } else {
            throw new NotValidLoginException("User not logged on");
        }

    }


    public void createNewFriendRecord(String currentUserName, String friendUserName) throws NotValidUserException {
        /*
        This method adds a new friend record where one does not exist.
        Since this is a new record, the default status of "Friend Requested" is updated to the record.
         */
        FriendValidations friendValidations = new FriendValidations();
        if (friendValidations.checkUserExists(friendUserName)) {
            User currentUser = getUser(currentUserName);
            User friendUser = getUser(friendUserName);
            UserFriend friend = new UserFriend();
            friend.setCurrentUser(currentUser);
            friend.setFriendUser(friendUser);
            friend.setStatus(FriendStatus.REQUESTED.friendStatus);

            SessionFactory sessionFactory = super.getSessionFactory();
            Session session = sessionFactory.openSession();
            //Begins a transaction
            session.beginTransaction();

            session.saveOrUpdate(friend);
            // session.save(user);
            //Commits the transaction in the DB
            session.getTransaction().commit();
            //  System.exit(0);
            session.close();
        }


    }

    public void updateFriendStatus(String currentUserName, String friendUserName, String status) throws NotValidUserException {

        FriendValidations friendValidations = new FriendValidations();
        if (friendValidations.checkUserExists(friendUserName)) {
            SessionFactory sessionFactory = super.getSessionFactory();
            Session session = sessionFactory.openSession();
            List<UserFriend> list = session
                    .createQuery(
                            " from UserFriend " +
                                    "where currentUser.userName =(:currentUser) AND friendUser.userName=(:friendUser) ")
                    .setParameter("currentUser", currentUserName)
                    .setParameter("friendUser", friendUserName)
                    .getResultList();
            for (UserFriend userFriend : list) {
                userFriend.setStatus(status);
                //Begins a transaction
                session.beginTransaction();
                //Saves the Customer789 object in the DB
                session.update(userFriend);
                // session.save(user);
                //Commits the transaction in the DB
                session.getTransaction().commit();
                //  System.exit(0);

            }
            session.close();
        }

    }


    public void deleteFriend(String currentUserName, String friendUserName){
        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();

        List<UserFriend> uf = session
                .createQuery(
                        " from UserFriend " +
                                "where currentUser.userName =(:currentUser) AND friendUser.userName=(:friendUser) ")
                .setParameter("currentUser", currentUserName)
                .setParameter("friendUser", friendUserName)
                .getResultList();
        for (UserFriend userFriend : uf) {
            userFriend.setStatus(FriendStatus.DELETED.friendStatus);
            //Begins a transaction
            session.beginTransaction();
            //Saves the Customer789 object in the DB
            session.update(userFriend);
            // session.save(user);
            //Commits the transaction in the DB
            session.getTransaction().commit();
            //  System.exit(0);

        }
        session.close();


    }

    public ArrayList viewFriend(String currentUser, String password) throws NotValidLoginException {
        boolean loggedOn = false;
        ArrayList friend = new ArrayList<>();

        loggedOn = isUserLoggedOn(currentUser, password);
        if (loggedOn) {
            friend = friends(currentUser);
        } else {
            throw new NotValidLoginException(currentUser + " is not logged on");
        }

        return friend;

    }

    public ArrayList viewFriendRequests(String currentUser, String password) throws NotValidLoginException {
        boolean loggedOn = false;
        ArrayList friend = new ArrayList<>();

        loggedOn = isUserLoggedOn(currentUser, password);
        if (loggedOn) {
            friend = viewFriendsToAdd(currentUser);
        } else {
            throw new NotValidLoginException(currentUser + " is not logged on");
        }

        return friend;

    }

    public ArrayList friends(String currentUser) {

        ArrayList<Friend> friendArray = new ArrayList<>();
        String output = "";

        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<com.company.objects.UserFriend> list = session
                .createQuery(
                        "from UserFriend " +
                                "where currentUser.userName =(:currentUser)")
                .setParameter("currentUser", currentUser)
                .getResultList();
        list.forEach((UserFriend userFriend) ->
        {
            String friendUserName = userFriend.getFriendUser().getUserName();
            String friendFirstName = userFriend.getFriendUser().getFirstName();
            String friendLastName = userFriend.getFriendUser().getLastName();
            String friendEmail = userFriend.getFriendUser().getEmail();
            String status = userFriend.getStatus();
            String currentUserName = userFriend.getCurrentUser().getUserName();
            Friend friend = new Friend(friendUserName, friendFirstName, friendLastName, friendEmail, status, currentUserName);

            friendArray.add(friend);

        });
        return friendArray;
    }

    public ArrayList viewFriendsToAdd(String currentUser) {

        ArrayList<Friend> friendArray = new ArrayList<>();
        String output = "";

        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<UserFriend> list = session
                .createQuery(
                        "from UserFriend " +
                                "where friendUser.userName =(:currentUser) AND status=(:status)")
                .setParameter("currentUser", currentUser)
                .setParameter("status", FriendStatus.REQUESTED.friendStatus)
                .getResultList();
        list.forEach((UserFriend userFriend) ->
        {
            /*
            As we are searching through the friend user column for the user and getting the Current user,
             */
            String currentUserName = userFriend.getFriendUser().getUserName();
            String friendUserName = userFriend.getCurrentUser().getUserName();
            String friendFirstName = userFriend.getCurrentUser().getFirstName();
            String friendLastName = userFriend.getCurrentUser().getLastName();
            String friendEmail = userFriend.getCurrentUser().getEmail();
            String getStatus = userFriend.getStatus();
            Friend friend = new Friend(friendUserName, friendFirstName, friendLastName, friendEmail, getStatus, currentUserName);

            friendArray.add(friend);

        });
        return friendArray;
    }

    public String backup(String currentUser) {
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
