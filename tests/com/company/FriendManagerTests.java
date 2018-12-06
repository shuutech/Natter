package com.company;

import com.company.controller.Controller;
import com.company.enums.FriendStatus;
import com.company.exceptions.NotValidLoginException;
import com.company.exceptions.NotValidUserException;
import com.company.managers.FriendManager;
import com.company.managers.Manager;
import com.company.objects.Friend;
import com.company.objects.UserFriend;
import com.company.validations.FriendValidations;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FriendManagerTests {

    @org.junit.jupiter.api.Test
    void invalidLogOn() throws NotValidLoginException {
        /*Test to see if a user can log on with invalid credential's */
        /* Test 1. Username and password do not exist */
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            new FriendManager().isUserLoggedOn("testUser", "testPassword");
        });
//        /* Test 2. Username and password do not match */
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            new FriendManager().isUserLoggedOn("shu", "wrong");
        });
        /* Test 3. Username and password match */
        assertTrue(new FriendManager().isUserLoggedOn("shu", "shu"));

        /* Test 4. No username or password entered */
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            new FriendManager().isUserLoggedOn(null, null);
        });

        /* Test 5. No password entered */
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            new FriendManager().isUserLoggedOn("shu", null);
        });

    }

    @org.junit.jupiter.api.Test
    void viewFriendsByStatus() {
        String s = null;
        FriendManager friendManager = new FriendManager();
        ArrayList<Friend> friends = friendManager.viewFriendsByStatus("tori", FriendStatus.REQUESTED.friendStatus);
        for (Friend f: friends){

            s = f.getCurrentUserName() + f.getStatus();

        }
        Assertions.assertEquals("rickyFriend Requested",s);

    }

    @org.junit.jupiter.api.Test
    void viewMoreFriendsByStatus() {
        String s = null;
        FriendManager friendManager = new FriendManager();
        ArrayList<Friend> friends = friendManager.viewFriendsByStatus("ricky", FriendStatus.REQUESTED.friendStatus);
        for (Friend f: friends){

            s = f.getCurrentUserName() + f.getStatus();

        }
        Assertions.assertEquals("rickyFriend Requested",s);

    }

    @org.junit.jupiter.api.Test
    void displayAllFriends() throws NotValidLoginException {
       new Controller().displayFriendsOfCurrentUser("tori", "tori");
        Assertions.assertEquals("rick",new Controller().displayFriendsOfCurrentUser("tori", "tori"));
    }



    @org.junit.jupiter.api.Test
    void requestFriendWithoutLogOn() {
/*
        Test to see if a user can add a friend without logging on
        1. A user cannot add friends with incorrect log in details
         */
        Assertions.assertThrows(NotValidLoginException.class, () -> {
           new FriendManager().addOrUpdateFriend("shu", "wrong", "ricky");
        });

        /*1. A non-user cannot add friends*/
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            new FriendManager().addOrUpdateFriend("fakeuser", "wrong", "ricky");
        });

        /*1. Null user cannot add friends*/
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            new FriendManager().addOrUpdateFriend(null, "wrong", "ricky");
        });

    }

    @org.junit.jupiter.api.Test
    void viewFriends() {
       /*
       The logged in user should be able to obtain a list of their friends stored in friend array.
       In this example, the user "ricky" has one friend "Al".
         */
        String name = null;

        ArrayList<Friend> friend = new FriendManager().friends("ricky");
        Assertions.assertEquals("shu", friend.get(0).getFriendUserName());
        Assertions.assertEquals("Al", friend.get(1).getFriendUserName());

    }

    @org.junit.jupiter.api.Test
    void viewMoreFriends() {
       /*
       The logged in user should be able to obtain a list of their friends stored in friend array.
       In this example, the user "ricky" has one friend "Al".
         */
        ArrayList<Friend> friend = new FriendManager().viewFriends("ricky");
        Assertions.assertEquals("torinado", friend.get(0).getFriendUserName());
        Assertions.assertEquals("tina", friend.get(1).getFriendUserName());

    }

    @org.junit.jupiter.api.Test
    void canRequestAFriend() throws NotValidLoginException, NotValidUserException {

         /*
       User should be able to add a new friend
         */
        FriendManager friendManager = new FriendManager();
        friendManager.addOrUpdateFriend("shu", "shu", "tina");
        ArrayList friends = friendManager.viewFriends("shu");
        Friend friend = new Friend("tina", "tina", "tina", "tina", "Friend Added","shu");
        boolean userExists = friends.stream().anyMatch(t -> t.equals(friend));

      assertTrue(userExists);

    }

    @org.junit.jupiter.api.Test
    void checkFriendStatus(){
       Assertions.assertEquals("Friend Requested",new FriendValidations().getFriendStatus("torinado", "ricky"));
        Assertions.assertEquals("Friend Added",new FriendValidations().getFriendStatus("emma", "shu"));

    }
    @Test
    void friendMustExist() {
        /*
        Cannot add a user that does not exist
         */
        Assertions.assertThrows(NotValidUserException.class, () -> {
  new FriendManager().addOrUpdateFriend("shu","shu","fake");
        });

        /*
        Cannot add a null friend
         */
        Assertions.assertThrows(NotValidUserException.class, () -> {
            new FriendManager().addOrUpdateFriend("shu","shu",null);
        });
    }

    @Test
    void updateFriend() throws NotValidUserException,NotValidLoginException{
 /*
        /*
        If friend is already friend with user, keep the status as friends

      */
 FriendValidations friendValidations= new FriendValidations();
 String status= FriendStatus.FRIENDS.friendStatus;

        Assertions.assertEquals(status,friendValidations.updateUserRecord("ricky","shu") );

        /*
        If Friend has already requested user to be friend, update them both as friends
         */

        Assertions.assertEquals(status,friendValidations.updateFriendRecord("torinado","ricky") );




    }

    @Test
    void keepStatusUnchanged() throws NotValidLoginException, NotValidUserException{

        String testUser="torinado";
        String password = "torinado";
        String testFriend="ricky";

        /*Test 1. If logged in testUser has already requested testFriend to be friends, then keep status as requested
         */
        new FriendManager().addOrUpdateFriend(testUser, password, testFriend);

        SessionFactory sessionFactory = new Manager().getSessionFactory();
        Session session = sessionFactory.openSession();
        List<UserFriend> list = session
                .createQuery(
                        "from UserFriend " +
                                "where currentUser.userName =(:currentUser) AND friendUser.userName=(:friendUser)")
                .setParameter("currentUser", testUser)
                .setParameter("friendUser", testFriend)
                .getResultList();
        list.forEach((UserFriend userFriend) ->
        {
            Assertions.assertEquals(FriendStatus.REQUESTED.friendStatus,userFriend.getStatus());
        });

    }

    @Test
    void addFriend() throws NotValidLoginException, NotValidUserException{

        String testUser="ricky";
        String testPassword="ricky";
        String testFriend="torinado";

        /*Test 1. If logged in testUser has received a friend request from testFriend, then set status to added
        For this to be true,
        Rule 1: logged in testUser must appear in the friendUser column of user testFriend.
        Rule 2: Current friend status must be "Friend Requested"
         */
        new FriendManager().addOrUpdateFriend(testUser, testPassword, testFriend);

        SessionFactory sessionFactory = new Manager().getSessionFactory();
        Session session = sessionFactory.openSession();
        List<UserFriend> list = session
                .createQuery(
                        "from UserFriend " +
                                "where currentUser.userName =(:currentUser) AND friendUser.userName=(:friendUser)")
                .setParameter("currentUser", testUser)
                .setParameter("friendUser", testFriend)
                .getResultList();
        list.forEach((UserFriend userFriend) ->
        {
            Assertions.assertEquals(FriendStatus.FRIENDS.friendStatus,userFriend.getStatus());
        });

    }

    @Test
    void requestDeletedFriend() throws NotValidLoginException, NotValidUserException{

        String testUser="tina";
        String testPassword="tina";
        String testFriend="ricky";

        /*Test 1. If logged in testUser has deleted testFriend, then set status to requested
         */
        new FriendManager().addOrUpdateFriend(testUser, testPassword, testFriend);

        SessionFactory sessionFactory = new Manager().getSessionFactory();
        Session session = sessionFactory.openSession();
        List<UserFriend> list = session
                .createQuery(
                        "from UserFriend " +
                                "where currentUser.userName =(:currentUser) AND friendUser.userName=(:friendUser)")
                .setParameter("currentUser", testUser)
                .setParameter("friendUser", testFriend)
                .getResultList();
        list.forEach((UserFriend userFriend) ->
        {
            Assertions.assertEquals(FriendStatus.REQUESTED.friendStatus,userFriend.getStatus());
        });

    }

    @Test
    void requestFriend() throws NotValidLoginException, NotValidUserException {

        String testUser="torinado";
        String testPassword="torinado";
        String testFriend="emma";

        /*Test 1. If logged in testUser wants to send Shiji a friend request, then set status to requested
         */
        new FriendManager().addOrUpdateFriend(testUser, testPassword, testFriend);

        SessionFactory sessionFactory = new Manager().getSessionFactory();
        Session session = sessionFactory.openSession();
        List<UserFriend> list = session
                .createQuery(
                        "from UserFriend " +
                                "where currentUser.userName =(:currentUser) AND friendUser.userName=(:friendUser)")
                .setParameter("currentUser", testUser)
                .setParameter("friendUser", testFriend)
                .getResultList();
        list.forEach((UserFriend userFriend) ->
        {
            Assertions.assertEquals(FriendStatus.REQUESTED.friendStatus,userFriend.getStatus());
        });

    }

    @Test
    void deleteFriend(){
        String currentUser = "ricky";
        String friendUser = "tina";
       FriendManager friendManager = new FriendManager();
       friendManager.deleteFriend(currentUser, friendUser);
       List<Friend> f = friendManager.viewFriends(currentUser);
        f.forEach((Friend friend) ->
        {
            if (friend.getFriendUserName().equals(friendUser)){
            Assertions.assertEquals(FriendStatus.DELETED.friendStatus,friend.getStatus());}
        });
    }


}