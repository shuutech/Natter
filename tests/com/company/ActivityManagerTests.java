package com.company;

import com.company.managers.UserManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ActivityManagerTests {
    @Test
    void invalidLogOn() {
        /*
        Test to see if a user can log on with an invalid password
        1. If a user does not exist, a message "Username or password is incorrect" should be displayed
        2. If password is incorrect, a message "Username or password is incorrect" should be displayed
         */
        Assertions.assertThrows(Exception.class, () -> {

            com.company.objects.User u = new UserManager().getUser("nonExistentUser");
        });

    }

    @org.junit.jupiter.api.Test
    void addActivitiesWithoutLogOn() {
     /*
        Test to see if a user can add activities without logging on first
        1. A user cannot add activities without logging on first
         */
    }

    @org.junit.jupiter.api.Test
    void viewActivitiesWithoutLogOn() {
     /*
        Test to see if a user can view activities without logging on first
        1. A user cannot view activities without logging on first
         */
    }

    @Test
    void addActivityToFriendList() {
        /*
        Activity will be added to friend list
         */

    }
    @Test
    void showAllActivities() {
         /*
        Displays activities for selected friend
         */


    }




}