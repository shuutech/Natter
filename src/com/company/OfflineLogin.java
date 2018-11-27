package com.company;

import com.company.managers.UserManager;

public class OfflineLogin {
    public void checkLogin(String username, String password){
        if (new UserManager().isUserLoggedOn(username, password)==true){
            System.out.println(username + "is authenticated");
        }else{
            System.out.println(username +" or " + password + " does not match");
        }
    }
}