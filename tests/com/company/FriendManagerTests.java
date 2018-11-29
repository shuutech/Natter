package com.company;

import com.company.enums.FriendStatus;
import com.company.exceptions.NotValidFriendException;
import com.company.exceptions.NotValidLoginException;
import com.company.exceptions.NotValidUserException;
import com.company.managers.FriendManager;
import com.company.managers.Manager;
import com.company.managers.UserManager;
import com.company.objects.Friend;
import com.company.objects.User;
import com.company.objects.UserFriend;
import com.company.validations.FriendValidations;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FriendManagerTests {

    @org.junit.jupiter.api.Test
    void invalidLogOn() throws NotValidLoginException, NotValidUserException {
        /*Test to see if a user can log on with invalid credentialis */
        /* Test 1. Username and password do not exist */
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            new FriendManager().isUserLoggedOn("testUser", "testPassword");
        });
//        /* Test 2. Username and password do not match */
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            new FriendManager().isUserLoggedOn("shu", "wrong");
        });
        /* Test 3. Username and password match */
        Assertions.assertTrue(new FriendManager().isUserLoggedOn("shu", "shu"));

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
    void requestFriendWithoutLogOn() {
/*
        Test to see if a user can add a friend without logging on
        1. A user cannot add friends with incorrect log in details
         */
        Assertions.assertThrows(NotValidLoginException.class, () -> {
      //      new FriendManager().updateFriend("shu", "wrong", "ricky", "Friend Requested");
        });

        /*1. A non-user cannot add friends*/
        Assertions.assertThrows(NotValidLoginException.class, () -> {
     //       new FriendManager().updateFriend("fakeuser", "wrong", "ricky", "Friend Requested");
        });

        /*1. Null user cannot add friends*/
        Assertions.assertThrows(NotValidLoginException.class, () -> {
      //      new FriendManager().updateFriend(null, "wrong", "ricky", "Friend Requested");
        });

    }

    @org.junit.jupiter.api.Test
    void viewFriends() {
       /*
       The logged in user should be able to obtain a list of their friends stored in friend array.
       In this example, the user "ricky" has one friend "Al".
         */

        ArrayList<Friend> friend = new FriendManager().friends("ricky");
        for (Friend f : friend) {
            Assertions.assertEquals("al", f.getFriendUserName());
        }
    }

    @org.junit.jupiter.api.Test
    void canRequestAFriend() throws NotValidLoginException, NotValidUserException {

         /*
       User should be able to add a new friend
         */
        FriendManager friendManager = new FriendManager();
  //      friendManager.updateFriend("shu", "shu", "al", "Friend Requested");
        ArrayList friends = friendManager.friends("shu");
       Friend friend = new Friend("al", "al", "al", "al", "Friend Requested","shu");
        boolean userExists = friends.stream().anyMatch(t -> t.equals(friend));

        assertTrue(userExists);

    }


    @org.junit.jupiter.api.Test
    void viewFriendsWithoutLogOn() throws Exception {
       /*
       A user that is not logged on will not be able to view friends
         */
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            new FriendManager().viewFriend("ricky", "wrong");
        });

              /*
       A user that is logged on will be able to view friends
         */
        ArrayList<Friend> friend1 = new FriendManager().viewFriend("ricky", "ricky");
        for (Friend f : friend1) {
            Assertions.assertEquals("al", f.getFriendUserName());
        }
                    /*
       A null user cannot view friends
         */
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            new FriendManager().viewFriend(null, null);
        });


    }



    @org.junit.jupiter.api.Test
    void checkFriendStatus(){
        Assertions.assertEquals("Friend Requested",new FriendValidations().getFriendStatus("shu", "ricky"));
    }
    @Test
    void friendMustExist() {
        /*
        Cannot add a user that does not exist
         */
        Assertions.assertThrows(NotValidUserException.class, () -> {
  //        new FriendManager().updateFriend("shu","shu","fake","Friend Requested");
        });

        /*
        Cannot add a null friend
         */
        Assertions.assertThrows(NotValidUserException.class, () -> {
     //       new FriendManager().updateFriend("shu","shu",null,"Friend Requested");
        });
    }

    @Test
    void updateFriend() throws NotValidUserException{
        /*
        If Friend has already requested user to be friend, update them both as friends
         */
     //   Assertions.assertEquals(FriendStatus.FRIENDS.friendStatus,new FriendValidations().updateFriendRecord("shu","manager") );
        /*
        If friend is already friend with user, keep the status as friends

      */

       // new FriendManager().updateFriend("shu", "ricky");
        //Assertions.assertEquals(FriendStatus.FRIENDS.friendStatus,new FriendValidations().updateFriendRecord("shu","manager") );

    }

    @Test
    void getFriendRecord(){
      /*
      This method returns a friend record from the database if it exists, regardless of whether the current user added
      the friend or vice versa
      */

      /*
      Test 1. Find friend record where the current logged in user is "Al" and friend is "Shu". Because "Shu" requested
      to be friends with Al first, we should be able to recover a record where the current user/friend user pair is reversed
      */
        ArrayList<Friend> list =   new FriendValidations().getFriendRecord("al","shu");
        for (Friend f: list){
            Assertions.assertEquals("shu al",f.getCurrentUserName() + " "+ f.getFriendUserName() );

        }
        /*
      Test 2. Find friend record where the current logged in user is "Shu" and friend is "Shu".
      */
        ArrayList<Friend> list1 =   new FriendValidations().getFriendRecord("shu","al");
        for (Friend f: list1){
            Assertions.assertEquals("shu al",f.getCurrentUserName() + " "+ f.getFriendUserName() );


        }

        /*
        Test 3. Get Null pointer exception where current username is not paired with friend user
        */
        Assertions.assertThrows(Exception.class, () -> {
            ArrayList<Friend> list2 =   new FriendValidations().getFriendRecord("no user","al");
            for (Friend f: list2){
                f.getCurrentUserName();
                f.getFriendUserName();

            }
        });

         /*
        Test 4. Get Null pointer exception where friend username is not paired with current user
        */
        Assertions.assertThrows(Exception.class, () -> {
            ArrayList<Friend> list3 =   new FriendValidations().getFriendRecord("al","no user");
            for (Friend f: list3){
                f.getCurrentUserName();
                f.getFriendUserName();

            }
        });



    }

    @Test
    void keepStatusUnchanged() throws NotValidLoginException, NotValidUserException{

        String testUser="shu";
        String password = "shu";
        String testFriend="ricky";

        /*Test 1. If logged in testUser has already requested testFriend to be friends, then keep status as requested
         */
        new FriendManager().addOrUpdateFriend(testUser, password, testFriend);

        SessionFactory sessionFactory = Manager.getSessionFactory();
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
    void updateStatusToAdded() throws NotValidLoginException, NotValidUserException{

        String testUser="ricky";
        String testPassword="ricky";
        String testFriend="shu";

        /*Test 1. If logged in testUser has received a friend request from testFriend, then set status to added
        For this to be true,
        Rule 1: logged in testUser must appear in the friendUser column of user testFriend.
        Rule 2: Current friend status must be "Friend Requested"
         */
        new FriendManager().addOrUpdateFriend(testUser, testPassword, testFriend);

        SessionFactory sessionFactory = Manager.getSessionFactory();
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

        String testUser="shu";
        String testPassword="shu";
        String testFriend="al";

        /*Test 1. If logged in testUser has deleted testFriend, then set status to requested
         */
        new FriendManager().addOrUpdateFriend(testUser, testPassword, testFriend);

        SessionFactory sessionFactory = Manager.getSessionFactory();
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

        String testUser="shiji";
        String testPassword="shiji";
        String testFriend="ricky";

        /*Test 1. If logged in testUser wants to send Shiji a friend request, then set status to requested
         */
        new FriendManager().addOrUpdateFriend(testUser, testPassword, testFriend);

        SessionFactory sessionFactory = Manager.getSessionFactory();
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
        String currentUser = "al";
        String friendUser = "shu";
       new FriendManager().deleteFriend(currentUser, friendUser);
        SessionFactory sessionFactory = Manager.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<UserFriend> list = session
                .createQuery(
                        "from UserFriend " +
                                "where currentUser.userName =(:currentUser) AND friendUser.userName=(:friendUser)")
                .setParameter("currentUser", currentUser)
                .setParameter("friendUser", friendUser)
                .getResultList();
        list.forEach((UserFriend userFriend) ->
        {
            Assertions.assertEquals(FriendStatus.DELETED.friendStatus,userFriend.getStatus());
        });

    }



//    public String updateFriendRecord(String friendUserName,String currentUserName)  {
//        String status = getFriendStatus(friendUserName, currentUserName);
//

//        If friend has unfriended the user, update the status to requested
//         */
//        } else if (status.equals(com.company.enums.FriendStatus.DELETED.toString())) {
//            status = com.company.enums.FriendStatus.REQUESTED.toString();
//             /*
//        If friend has not requested or added user as friend, update the status to requested
//         */
//        } else {
//            status = com.company.enums.FriendStatus.REQUESTED.toString();
//        }
//        return status;
//    }
//
//    public String updateUserRecord(String currentUserName,String friendUserName)  {
//        String status = getFriendStatus(currentUserName, friendUserName);
//
//        /*
//        If user has already requested friend to be friend, update them both as friends
//         */
//        if (status.equals(com.company.enums.FriendStatus.REQUESTED.toString())) {
//            status = com.company.enums.FriendStatus.REQUESTED.toString();
//           /*
//        If user is already friend with friend, keep the status as friends
//         */
//        } else if (status.equals(com.company.enums.FriendStatus.FRIENDS.toString())) {
//            status = com.company.enums.FriendStatus.FRIENDS.toString();
//            /*
//        If user has unfriended the friend, update the status to requested
//         */
//        } else if (status.equals(com.company.enums.FriendStatus.DELETED.toString())) {
//            status = com.company.enums.FriendStatus.REQUESTED.toString();
//             /*
//        If user has not requested or added friend as friend, update the status to requested
//         */
//        } else {
//            status = FriendStatus.REQUESTED.toString();
//        }
//        return status;
//    }
//
//    }

    @Test
    void friendRequestAccepted() {
       /*
        A user must be able to approve the friend request before becoming friends
         */

    }

    @Test
    void friendApproved() {
     /*
     Once a friend request is approved, the friend status should show as "friends"
         */
    }

    @Test
    void viewAllFriends() {
     /*
     User must be able to view all their friends and friend requests
      */
    }

    @Test
    void deleteAFriend() {
     /*
        User must be able to delete a friendship pairing
         */
    }


}