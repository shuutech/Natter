package com.company.offline;

import com.company.exceptions.NotValidLoginException;
import com.company.exceptions.NotValidUserException;
import com.company.managers.FriendManager;
import com.company.objects.Friend;


import java.util.ArrayList;

public class OfflineMethods {

    public void displayFriends(String username, String password) {
        System.out.println("Displaying friends for " + username );
        try {
            ArrayList<Friend> friend = new FriendManager().viewFriend(username, password);
            friend.forEach((Friend f) ->{
                System.out.println("Friend: " + f.getFriendFirstName() + " " + f.getFriendLastName());
            });
        }
        catch(NotValidLoginException e)
        {
            e.getMessage();
        }
    }
}
