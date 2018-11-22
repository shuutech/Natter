package com.company;

public enum FriendStatus {REQUESTED("Friend Requested"), DELETED("Friend Deleted"), FRIENDS("Friends");
    String friendStatus = null;

    FriendStatus (String friendStatus){
        this.friendStatus = friendStatus;
    }
}
