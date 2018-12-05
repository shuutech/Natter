package com.company.controller;

import com.company.exceptions.NotValidLoginException;
import com.company.managers.FriendManager;
import com.company.objects.Friend;

import java.util.ArrayList;

public class Controller {
    public String displayFriendsOfCurrentUser(String username, String password) {
        FriendManager friendManager = new FriendManager();
        String output="[";
        int i=0;
        try {
            if (friendManager.isUserLoggedOn(username,password)){
                ArrayList<Friend> friend = friendManager.friends(username);
                for (Friend f: friend){
                         output +=
                                "{" +
                                        "\"currentUserName\":\"" + f.getCurrentUserName() + '\"' +
                                        ",\"friendUserName\":\"" + f.getFriendUserName() + '\"' +
                                        ", \"friendFirstName\":\"" + f.getFriendFirstName() + '\"' +
                                        ", \"friendLastName\":\"" + f.getFriendLastName() + '\"' +
                                        ", \"friendEmail\":\"" + f.getFriendEmail() + '\"' +
                                        ", \"status\":\"" + f.getStatus() + '\"' ;
                        i++;
                        if (i< friend.size()){
                            output+=          "},";
                        } else {
                            output+=          "}";
                        }
                    }
                output+=          "]";
            }}
            catch(NotValidLoginException e)
            {
            return "Looks like you may have entered the wrong login details in";
            }
        return output;
    }

    public String displayFriendsOfFriendUser(String username, String password, String status) {
        FriendManager friendManager = new FriendManager();
        String output=null;
        try {
            if (friendManager.isUserLoggedOn(username,password)){
                ArrayList<Friend> friend= friendManager.viewFriends(username);
                for (Friend f: friend){
                    if (f.getStatus().equals(status)){
                        output =
                                "{" +
                                        "\"currentUserName\":\"" + f.getCurrentUserName() + '\"' +
                                        ",\"friendUserName\":\"" + f.getFriendUserName() + '\"' +
                                        ", \"friendFirstName\":\"" + f.getFriendFirstName() + '\"' +
                                        ", \"friendLastName\":\"" + f.getFriendLastName() + '\"' +
                                        ", \"friendEmail\":\"" + f.getFriendEmail() + '\"' +
                                        ", \"status\":\"" + f.getStatus() + '\"' +
                                        '}';
                    }}
            }}
        catch(NotValidLoginException e)
        {
            return "Looks like you may have entered the wrong login details in";
        }
        return output;
    }
}
