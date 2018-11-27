package com.company;

import com.company.exceptions.NotValidLoginException;
import com.company.managers.FriendManager;
import com.company.managers.UserManager;
import com.company.objects.Friend;
import com.company.objects.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class FriendManagerTests {

    @org.junit.jupiter.api.Test
    void invalidLogOn() {
        /*Test to see if a user can log on with invalid credentialis */
        /* Test 1. Username and password do not exist */
        Assertions.assertFalse(new FriendManager().isUserLoggedOn("testUser", "testPassword"));
        /* Test 2. Username and password do not match */
        Assertions.assertFalse(new FriendManager().isUserLoggedOn("shu", "wrong"));
        /* Test 2. Username and password match */
        Assertions.assertTrue(new FriendManager().isUserLoggedOn("shu", "shu"));

    }

    @org.junit.jupiter.api.Test
    void addFriendWithoutLogOn() {
/*
        Test to see if a user can add a friend without logging on
        1. A user cannot add friends with incorrect log in details
         */
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            FriendManager friendManager = new FriendManager();
            boolean loggedOn = friendManager.isUserLoggedOn("shu", "wrong");
            if (loggedOn) {
                friendManager.addFriend("shu", "wrong", "Joined");
            } else {
                throw new NotValidLoginException("User not logged on");
            }
        });

        /*1. A non-user cannot add friends*/
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            FriendManager friendManager = new FriendManager();
            boolean loggedOn = friendManager.isUserLoggedOn("testuser", "testpassword");
            if (loggedOn) {
                friendManager.addFriend("testuser", "testpassword", "Joined");
            } else {
                throw new NotValidLoginException("User not logged on");
            }
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
        void viewFriendsWithoutLogOn() {
       /*
       A user that is not logged on will not be able to view friends
         */
            ArrayList<Friend> friend = new  FriendManager().viewFriend("ricky","wrong");
            for (Friend f: friend ){
                Assertions.assertEquals(null, f.getFriendUserName());
            }



    }

    @Test
    void friendMustExist() {
        /*
        Cannot end a user that does not exist
         */

    }

    @Test
    void friendNotAlreadyAFriend() {
         /*
        User cannot add a friend that is already a friend
         */


    }

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