package com.company;

import com.company.controller.Controller;
import com.company.enums.FriendStatus;
import com.company.exceptions.NotValidLoginException;
import com.company.exceptions.NotValidUserException;
import com.company.managers.Manager;
import com.company.managers.UserManager;
import com.company.objects.User;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserManagerTests {


    @org.junit.jupiter.api.Test
    void enteredIntoDatabase() throws Exception {

 new UserManager().addUserToDB("new", "new", "sdf", "sdf", "sdf", "sdf", "sdf");
    }

    @org.junit.jupiter.api.Test
    void getUserMustBeValid() throws NotValidUserException{
   /*
       1. Returns valid user from database when method is called
      */
        Assertions.assertEquals("ricky", new UserManager().getUser("ricky").getUserName());

         /*
       2. Returns NotValidUserException when invalid user is entered
      */
        Assertions.assertThrows(NotValidUserException.class, () -> {
            new UserManager().getUser("fake").getUserName();
        });

           /*
       3. Returns NotValidUserException when null user is entered
      */
        Assertions.assertThrows(NotValidUserException.class, () -> {
            new UserManager().getUser(null).getUserName();
        });

    }
    @org.junit.jupiter.api.Test
    void deleteUser() throws NotValidUserException{
        new UserManager().delete("christy");

    }
    @org.junit.jupiter.api.Test
    void displayFriends() throws NotValidLoginException
    {
        String compare = "[{\"currentUserName\":\"tori\",\"friendUserName\":\"shu\", \"friendFirstName\":\"shu\", \"friendLastName\":\"shu\", \"friendEmail\":\"s\", \"status\":\"Friend Requested\"}]";
        String s = new Controller().displayFriendsOfCurrentUser("tori", "tori");
    Assertions.assertEquals(compare,s);
    }
}