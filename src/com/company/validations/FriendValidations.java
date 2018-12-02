package com.company.validations;
import com.company.enums.FriendStatus;
import com.company.exceptions.NotValidLoginException;
import com.company.exceptions.NotValidUserException;
import com.company.managers.FriendManager;
import com.company.objects.Friend;

import java.util.ArrayList;

public class FriendValidations {
    FriendManager friendManager = new FriendManager();

    public Boolean checkUserExists(String name) throws NotValidUserException {
        boolean pass = true;
        if (friendManager.getUser(name) == null) {
            pass = false;
            throw new NotValidUserException(name + " does not exist");
        }
        return pass;
    }

    public String getFriendStatus(String currentUserName, String friendUserName)  {
        String status = null;
        ArrayList<Friend> friend = new FriendManager().friends(currentUserName);
        for (Friend f : friend) {
            if (f.getFriendUserName().equals(friendUserName)) {
                status = f.getStatus();
            }
        }
        return status;
    }

    public ArrayList getFriendRecord(String currentUserName, String friendUserName)  {
        //check if a friend record of the user and friend already exists
        ArrayList<Friend> userHasFriend = new FriendManager().friends(currentUserName);
        ArrayList<Friend> record = new ArrayList<>();
        for (Friend f : userHasFriend) {
            if (f.getFriendUserName().equals(friendUserName)) {
                record.add( new Friend(f.getFriendUserName(),f.getFriendFirstName(), f.getFriendLastName(), f.getFriendEmail(), f.getStatus(),f.getCurrentUserName()));
                return record;
            }
        }
        ArrayList<Friend> friendHasUser = new FriendManager().friends(friendUserName);
        for (Friend f : friendHasUser) {
            if (f.getFriendUserName().equals(currentUserName)) {
                record.add( new Friend(f.getFriendUserName(),f.getFriendFirstName(), f.getFriendLastName(), f.getFriendEmail(), f.getStatus(),f.getCurrentUserName()));
                return record;
            }
        }
        return null;
    }

    public String updateFriendRecord(String currentUserName,String friendUserName)   {
        String status = getFriendStatus(currentUserName, friendUserName);


        if (status==null) {
            status = FriendStatus.REQUESTED.friendStatus;

        /*
        If Friend has already requested user to be friend, update them both as friends
         */
        } else if (status.equals(FriendStatus.REQUESTED.friendStatus)) {
            status = FriendStatus.FRIENDS.friendStatus;
           /*
        If friend is already friend with user, keep the status as friends
         */
        } else if (status.equals(FriendStatus.FRIENDS.friendStatus)) {
            status = FriendStatus.FRIENDS.friendStatus;
            /*
        If friend has unfriended the user, update the status to requested
         */
        } else if (status.equals(FriendStatus.DELETED.friendStatus)) {
            status = FriendStatus.REQUESTED.friendStatus;
             /*
        If friend has not requested or added user as friend, update the status to requested
         */
        } else {
            status = FriendStatus.REQUESTED.friendStatus;
        }
        return status;
    }

    public String updateUserRecord(String currentUserName,String friendUserName)  {
        String status = getFriendStatus(currentUserName, friendUserName);


        if (status==null) {
            status = FriendStatus.REQUESTED.friendStatus;
          /*
        If user has already requested friend to be friend, update them both as friends
         */
        } else if (status.equals(FriendStatus.REQUESTED.friendStatus)) {
            status = FriendStatus.REQUESTED.friendStatus;
           /*
        If user is already friend with friend, keep the status as friends
         */
        } else if (status.equals(FriendStatus.FRIENDS.friendStatus)) {
            status = FriendStatus.FRIENDS.friendStatus;
            /*
        If user has unfriended the friend, update the status to requested
         */
        } else if (status.equals(FriendStatus.DELETED.friendStatus)) {
            status = FriendStatus.REQUESTED.friendStatus;
             /*
        If user has not requested or added friend as friend, update the status to requested
         */
        } else {
            status = FriendStatus.REQUESTED.friendStatus;
        }
        return status;
    }
}


