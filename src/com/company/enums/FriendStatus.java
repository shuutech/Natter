package com.company.enums;

public enum FriendStatus {REQUESTED("Friend Requested"), DELETED("Friend Deleted"), FRIENDS("Friends");
    public String friendStatus = null;

     FriendStatus (String friendStatus){
        this.friendStatus = friendStatus;
    }
}
