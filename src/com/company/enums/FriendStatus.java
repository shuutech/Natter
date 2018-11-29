package com.company.enums;

public enum FriendStatus {REQUESTED("Friend Requested"), DELETED("Friend Deleted"), FRIENDS("Friend Added");
    public String friendStatus = null;

     FriendStatus (String friendStatus){
        this.friendStatus = friendStatus;
    }
}
