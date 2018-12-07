package com.company;

import com.company.controller.Controller;
import com.company.enums.FriendStatus;
import com.company.exceptions.NotValidLoginException;
import com.company.exceptions.NotValidUserException;
import com.company.managers.Manager;
import com.company.managers.UserManager;
import com.company.objects.User;
import com.company.validations.UserValidations;
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
    void validateUserName() throws Exception {
//        //No upper case
        Assertions.assertThrows(Exception.class, () -> {
            new UserValidations().validUserName("SHUSHU");
        });
//        //Length too short

        Assertions.assertThrows(Exception.class, () -> {
            new UserValidations().validUserName("shu");
        });
        //doesn't contain numbers
        Assertions.assertTrue(new UserValidations().validUserName("shuubiz"));
        // contains numbers
        Assertions.assertTrue(new UserValidations().validUserName("shuubiz23"));
        //Contains special characters
//        Assertions.assertThrows(Exception.class, () -> {
//            new UserValidations().validUserName("shu!@#");
//        });
    }
    @org.junit.jupiter.api.Test
    void validateFirstName()throws Exception  {
        //Length too short
        Assertions.assertThrows(Exception.class, () -> {new UserValidations().validFirstName("Sa");});
        //Contains number
        Assertions.assertThrows(Exception.class, () -> {new UserValidations().validFirstName("Shu1");});
        //contains space
        Assertions.assertTrue(new UserValidations().validFirstName("Shu shu"));
        //contains upper and lowercase
        Assertions.assertTrue(new UserValidations().validFirstName("ShuShu"));
        //first character not capital
        Assertions.assertThrows(Exception.class, () -> {new UserValidations().validFirstName("shu Shu");});
    }

    @org.junit.jupiter.api.Test
    void validateLastName()throws Exception  {
        //Length too short
        Assertions.assertFalse(new UserValidations().validLastName("Sa"));
        //Contains number
        Assertions.assertFalse(new UserValidations().validLastName("Shu1"));
        //contains space
        Assertions.assertTrue(new UserValidations().validLastName("Shu shu"));
        //contains upper and lowercase
        Assertions.assertTrue(new UserValidations().validLastName("ShuShu"));
        //first character not capital
        Assertions.assertFalse(new UserValidations().validLastName("shu Shu"));
    }

    @org.junit.jupiter.api.Test
    void validateLocationName()throws Exception  {
        //Length too short
        Assertions.assertFalse(new UserValidations().validLastName("M"));
        //Contains number
        Assertions.assertFalse(new UserValidations().validLastName("Mel1"));
        //contains space
        Assertions.assertTrue(new UserValidations().validLastName("New England"));
        //contains upper and lowercase
        Assertions.assertTrue(new UserValidations().validLastName("Vic"));
        //first character not capital
        Assertions.assertFalse(new UserValidations().validLastName("vic"));
    }

    @org.junit.jupiter.api.Test
    void validatePasswordName() throws Exception {
        //At least 6 characters
        Assertions.assertThrows(Exception.class, () -> {new UserValidations().validPassword("Abc1$");});
        //Contains at least one uppercase
        Assertions.assertThrows(Exception.class, () -> {new UserValidations().validPassword("abcdef1$");});
//        //contains at least one lowercase
        Assertions.assertThrows(Exception.class, () -> {new UserValidations().validPassword("ABCDEFG1$");});
//        //contains at least one number
        Assertions.assertThrows(Exception.class, () -> {new UserValidations().validPassword("Abcdefg$");});
//        //contains at least one symbol
        Assertions.assertThrows(Exception.class, () -> {new UserValidations().validPassword("abcdef1H");});
//        //valid
        Assertions.assertTrue(new UserValidations().validPassword("Abcdef1%"));
    }

    @org.junit.jupiter.api.Test
    void validatePhone()throws Exception  {

        Assertions.assertTrue(new UserValidations().validPhone("0422818616"));
        Assertions.assertFalse(new UserValidations().validPhone("sef0422818616"));
    }
    @org.junit.jupiter.api.Test
    void validateEmail()throws Exception  {

        Assertions.assertTrue(new UserValidations().validEmail("shu.chen@anz.com"));
        Assertions.assertFalse(new UserValidations().validEmail("shu.chen@anz..com"));
        Assertions.assertFalse(new UserValidations().validPhone("shu@anz.com."));
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