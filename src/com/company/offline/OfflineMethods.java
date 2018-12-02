package com.company.offline;

import com.company.enums.FriendStatus;
import com.company.exceptions.NotValidLoginException;
import com.company.exceptions.NotValidUserException;
import com.company.managers.ActivityManager;
import com.company.managers.FriendManager;
import com.company.objects.Friend;


import java.util.ArrayList;

public class OfflineMethods {

    public void displayFriendsOfCurrentUser(String username, String password, String status) {
FriendManager friendManager = new FriendManager();
        try {
            if (friendManager.isUserLoggedOn(username,password)){
            ArrayList<Friend> friend = friendManager.friends(username);
            System.out.println("Displaying " + status +" for " + username );
            friend.forEach((Friend f) ->{
                if (f.getStatus().equals(status)){
                    System.out.println(f.getCurrentUserName() + " " + f.getStatus()+ " " + f.getFriendFirstName() + " " + f.getFriendLastName() );
            }});
            System.out.println("Note: If you initiated the friend request, you will need to wait for your friend to add you");
        }}
        catch(NotValidLoginException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void displayFriendsOfFriendUser(String username, String password, String status) {
        FriendManager friendManager = new FriendManager();
        try {
            if (friendManager.isUserLoggedOn(username,password)){
            ArrayList<Friend> friend= friendManager.viewFriends(username);
            friend.forEach((Friend f) ->{
                if (f.getStatus().equals(status)){
                    System.out.println(f.getFriendFirstName() + " " + f.getFriendLastName() + " "+ f.getStatus() + " "+ f.getCurrentUserName() );
                }});
        }}
        catch(NotValidLoginException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void addOrRequestFriend(String username, String password, String friendname){
        try {
            new FriendManager().addOrUpdateFriend(username, password, friendname);
        }catch(Exception e ){
            System.out.println(e.getMessage());
        }
        displayFriendsOfCurrentUser(username,password,FriendStatus.REQUESTED.friendStatus);
        displayFriendsOfFriendUser(username,password,FriendStatus.REQUESTED.friendStatus);


    }

    public void viewActivities(String username, String password){
        ActivityManager activityManager = new ActivityManager();
        try {
            if (activityManager.isUserLoggedOn(username, password))
            {
                for (String viewActivity: activityManager.viewAvailableActivity(username)){
                    System.out.println(viewActivity);
            }
            }
        }catch(NotValidLoginException e){e.getMessage();}
    }
}
