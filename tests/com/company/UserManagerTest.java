package com.company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    @org.junit.jupiter.api.Test
    void doesUserExist() {
        Assertions.assertThrows(Exception.class, () -> {

          User u = new UserManager().getUser("nonExistentUser");
        });

    }



    @org.junit.jupiter.api.Test
    void emailAddressNotNull() {
        Assertions.assertThrows(Exception.class, () -> {

             new User().setEmail(null);
        });
    }

    @Test
    void isUserLoggedOn1() {
    }

    @Test
    void getUser1() {
    }
}