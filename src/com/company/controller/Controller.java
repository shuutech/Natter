package com.company.controller;

import com.company.enums.FriendStatus;
import com.company.exceptions.NotValidLoginException;
import com.company.managers.FriendManager;
import com.company.objects.Friend;

import java.util.ArrayList;

public class Controller {
    /**
     * Displays a list of all friends the user has or the friend requests that are pending for the user to approve.
     * @param username
     * @param password
     * @return
     */
    public String displayFriendsOfCurrentUser(String username, String password) throws NotValidLoginException{
        FriendManager friendManager = new FriendManager();
        String output="[";
        int i=0;

            if (friendManager.isUserLoggedOn(username,password)){

                //List of all friends that the user is friends with excluding the friend requests the user has sent
                ArrayList<Friend> friendMinusRequests = friendManager.friendsByStatus(username, FriendStatus.FRIENDS.friendStatus);
                //List of all friends that the user is friends with including the friend requests the user has to review.
                ArrayList<Friend> allFriends = friendManager.viewFriends(username);

                ArrayList<Friend> combined = new ArrayList<>();
                combined.addAll(friendMinusRequests);
                combined.addAll(allFriends);
                for (Friend f: combined){
                    output +=
                            "{" +
                                    "\"currentUserName\":\"" + f.getCurrentUserName() + '\"' +
                                    ",\"friendUserName\":\"" + f.getFriendUserName() + '\"' +
                                    ", \"friendFirstName\":\"" + f.getFriendFirstName() + '\"' +
                                    ", \"friendLastName\":\"" + f.getFriendLastName() + '\"' +
                                    ", \"friendEmail\":\"" + f.getFriendEmail() + '\"' +
                                    ", \"status\":\"" + f.getStatus() + '\"' ;
                    i++;
                    if (i< combined.size()){
                        output+=          "},";
                    } else {
                        output+=          "}";
                    }
                }
                output+=          "]";
            }
        return output;
    }

    /**
     * Displays all the friend requests sent by current user.
     * @param username
     * @param password
     * @return
     */
    public String displayFriendRequests(String username, String password) throws NotValidLoginException {
        FriendManager friendManager = new FriendManager();
        String output="[";
        int i=0;
            if (friendManager.isUserLoggedOn(username,password)){

                //List of all friends that the user is friends with excluding the friend requests the user has submitted
                ArrayList<Friend> friendRequests = friendManager.friendsByStatus(username, FriendStatus.REQUESTED.friendStatus);

                for (Friend f: friendRequests){
                    output +=
                            "{" +
                                    "\"currentUserName\":\"" + f.getCurrentUserName() + '\"' +
                                    ",\"friendUserName\":\"" + f.getFriendUserName() + '\"' +
                                    ", \"friendFirstName\":\"" + f.getFriendFirstName() + '\"' +
                                    ", \"friendLastName\":\"" + f.getFriendLastName() + '\"' +
                                    ", \"friendEmail\":\"" + f.getFriendEmail() + '\"' +
                                    ", \"status\":\"" + f.getStatus() + '\"' ;
                    i++;
                    if (i< friendRequests.size()){
                        output+=          "},";
                    } else {
                        output+=          "}";
                    }
                }
                output+=          "]";
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
