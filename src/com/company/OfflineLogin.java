package com.company;

import com.company.exceptions.NotValidLoginException;
import com.company.managers.UserManager;

public class OfflineLogin {
    public void checkLogin(String username, String password){

        Boolean loggedOn;
        try{
            loggedOn=  new UserManager().isUserLoggedOn(username, password);
            if (loggedOn==true){
                System.out.println(username + "is authenticated");
            }
        }catch(Exception e){
            e.getMessage();
        }

//        if (new UserManager().isUserLoggedOn(username, password)==true){
//            System.out.println(username + "is authenticated");
//        }else{
//            System.out.println(username +" or " + password + " does not match");
//        }
    }
}