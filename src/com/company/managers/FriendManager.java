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

    /**
     * See {@link UserManager#isUserLoggedOn(String, String)} ()} )
     */
    public boolean isUserLoggedOn(String username, String password) throws NotValidLoginException {
        return new UserManager().isUserLoggedOn(username, password);
    }
    /**
     * See {@link UserManager#getUser(String)} (String, String)} ()} )
     */
    public User getUser(String username) throws NotValidUserException {
        return new UserManager().getUser(username);
    }
    /**
     * Enables a logged in user to request or add a friend.
     * <br> Once user is logged in {@link #isUserLoggedOn(String, String)}, obtain all friend records of the user.
     * <br> If a friend record of the user and the friend user does not exist then create a new record {@link #createNewFriendRecord(String, String)}.
     * <br> If a friend record between user and friend exists, obtain the status of the friendship
     * <br> If user has already requested the friend to be a user, then keep the friend status as Friend Requested. {@link FriendValidations#updateUserRecord(String, String)},
     * <br> If friend has submitted a friend request to the user, then update the status to Friend Added. {@link FriendValidations#updateFriendRecord(String, String)},
     * @param currentUserName Username of current user
     * @param password Password of current user
     * @param friendUserName Username of friend that user wants to add
     */
    public void addOrUpdateFriend(String currentUserName, String password, String friendUserName) throws NotValidLoginException, NotValidUserException {
        FriendValidations friendValidations = new FriendValidations();
        boolean loggedOn = isUserLoggedOn(currentUserName, password);
        String status;
        if (loggedOn) {
            ArrayList<Friend> friend = friendValidations.getFriendRecord(currentUserName, friendUserName);
            if (friend == null) {
                createNewFriendRecord(currentUserName, friendUserName);
            } else {
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
            throw new NotValidLoginException(currentUserName + " user is not logged on");
        }
    }

    /**
     * Adds a new entry to the UserFriend table with the status of "Friend Requested"
     * <br>After validating that the friendUserName exists in the User table{@link FriendValidations#checkUserExists(String)}, then
     * <br>1. Retrieve the current user from the User table{@link #getUser(String)} where String is currentUserName
     * <br>2. Retrieve the friend user form the User table{@link #getUser(String)} where String is friendUserName
     * <br>3. Create a new entry in the UserFriend table with the new friendship pair and set the status to "Friend Requested"
     * @param currentUserName Username of current user
     * @param friendUserName Username of friend that user wants to add
     * @throws NotValidUserException
     */
    public void createNewFriendRecord(String currentUserName, String friendUserName) throws NotValidUserException {

        FriendValidations friendValidations = new FriendValidations();
        if (friendValidations.checkUserExists(friendUserName)) {
            User currentUser = getUser(currentUserName);
            User friendUser = getUser(friendUserName);
            UserFriend friend = new UserFriend();
            friend.setCurrentUser(currentUser);
            friend.setFriendUser(friendUser);
            friend.setStatus(FriendStatus.REQUESTED.friendStatus);
            super.saveOrUpdate(friend);
        }
    }

    /**
     * Queries the UserFriend database for a list of all records where the logged in user appears in the current user column
     * and the friend user appears in the friendUser column.
     * <br>After validating that the friendUserName exists in the User table{@link FriendValidations#checkUserExists(String)}, then
     * @param currentUserName
     * @param friendUserName
     * @param status
     * @throws NotValidUserException
     */
    public void updateFriendStatus(String currentUserName, String friendUserName, String status) throws NotValidUserException {
        FriendValidations friendValidations = new FriendValidations();
        if (friendValidations.checkUserExists(friendUserName)) {
            Session session = super.openSession();
            List<UserFriend> list = session
                    .createQuery(
                            " from UserFriend " +
                                    "where currentUser.userName =(:currentUser) AND friendUser.userName=(:friendUser) ")
                    .setParameter("currentUser", currentUserName)
                    .setParameter("friendUser", friendUserName)
                    .getResultList();
            for (UserFriend userFriend : list) {
                userFriend.setStatus(status);
                super.update(session, userFriend);
            }
            session.close();
        }
    }


    public void deleteFriend(String currentUserName, String friendUserName) {
        Session session = super.openSession();
        List<UserFriend> uf = session
                .createQuery(
                        " from UserFriend " +
                                "where currentUser.userName =(:currentUser) AND friendUser.userName=(:friendUser) ")
                .setParameter("currentUser", currentUserName)
                .setParameter("friendUser", friendUserName)
                .getResultList();
        for (UserFriend userFriend : uf) {
            userFriend.setStatus(FriendStatus.DELETED.friendStatus);
            super.update(session, userFriend);
        }
        session.close();
        Session session1 = super.openSession();
        List<UserFriend> uf1 = session1
                .createQuery(
                        " from UserFriend " +
                                "where friendUser.userName =(:currentUser) AND currentUser.userName=(:friendUser) ")
                .setParameter("currentUser", currentUserName)
                .setParameter("friendUser", friendUserName)
                .getResultList();
        for (UserFriend userFriend : uf1) {
            userFriend.setStatus(FriendStatus.DELETED.friendStatus);
            super.update(session1, userFriend);
        }
        session1.close();
    }


    public ArrayList<Friend> friends(String currentUser) {

        ArrayList<Friend> friendArray = new ArrayList<>();
        Session session = super.openSession();
        List<UserFriend> list = session
                .createQuery(
                        "from UserFriend " +
                                "where currentUser.userName =(:currentUser)")
                .setParameter("currentUser", currentUser)
                .getResultList();
        list.forEach((UserFriend userFriend) ->
        {
            friendArray.add(new Friend(
                    userFriend.getFriendUser().getUserName(),
                    userFriend.getFriendUser().getFirstName(),
                    userFriend.getFriendUser().getLastName(),
                    userFriend.getFriendUser().getEmail(),
                    userFriend.getStatus(),
                    userFriend.getCurrentUser().getUserName()
            ));

        });
        session.close();
        return friendArray;

    }

    public ArrayList<Friend> viewFriends(String currentUser) {

        ArrayList<Friend> friendArray = new ArrayList<>();
        Session session = super.openSession();
        List<UserFriend> list = session
                .createQuery(
                        "from UserFriend where friendUser.userName =(:currentUser)")
                .setParameter("currentUser", currentUser)
                .getResultList();

        list.forEach((UserFriend userFriend) ->
        {
            /*
            As we are searching through the friend user column for the user and getting the Current user,
             */
            friendArray.add(new Friend(
                    userFriend.getCurrentUser().getUserName(),
                    userFriend.getCurrentUser().getFirstName(),
                    userFriend.getCurrentUser().getLastName(),
                    userFriend.getCurrentUser().getEmail(),
                    userFriend.getStatus(),
                    userFriend.getFriendUser().getUserName()));
        });
        session.close();
        return friendArray;

    }

    public ArrayList<Friend> friendsByStatus(String currentUser, String status) {

        ArrayList<Friend> friendArray = new ArrayList<>();
        Session session = super.openSession();
        List<UserFriend> list = session
                .createQuery(
                        "from UserFriend where currentUser.userName =(:currentUser) and status=(:status)")
                .setParameter("currentUser", currentUser)
                .setParameter("status", status)
                .getResultList();

        list.forEach((UserFriend userFriend) ->
        {
            /*
            As we are searching through the friend user column for the user and getting the Current user,
             */
            friendArray.add(new Friend(
                    userFriend.getFriendUser().getUserName(),
                    userFriend.getFriendUser().getFirstName(),
                    userFriend.getFriendUser().getLastName(),
                    userFriend.getFriendUser().getEmail(),
                    userFriend.getStatus(),
                    userFriend.getCurrentUser().getUserName()));
        });
        session.close();
        return friendArray;

    }

    public ArrayList<Friend> viewFriendsByStatus(String friendUser, String status) {

        ArrayList<Friend> friendArray = new ArrayList<>();
        Session session = super.openSession();
        List<UserFriend> list = session
                .createQuery(
                        "from UserFriend where friendUser.userName =(:friendUser) and status=(:status)")
                .setParameter("friendUser", friendUser)
                .setParameter("status", status)
                .getResultList();

        list.forEach((UserFriend userFriend) ->
        {
            /*
            As we are searching through the friend user column for the user and getting the Current user,
             */
            friendArray.add(new Friend(
                    userFriend.getCurrentUser().getUserName(),
                    userFriend.getCurrentUser().getFirstName(),
                    userFriend.getCurrentUser().getLastName(),
                    userFriend.getCurrentUser().getEmail(),
                    userFriend.getStatus(),
                    userFriend.getFriendUser().getUserName()));
        });
        session.close();
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
