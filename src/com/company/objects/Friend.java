package com.company.objects;

import java.util.ArrayList;

public class Friend {
    String friendUserName;
    String friendFirstName;
    String friendLastName;
    String friendEmail;
    String status;


    public Friend() {

    }

    public Friend(String friendUserName, String friendFirstName, String friendLastName, String friendEmail, String status) {
        this.friendUserName = friendUserName;
        this.friendFirstName = friendFirstName;
        this.friendLastName = friendLastName;
        this.friendEmail = friendEmail;
        this.status = status;
    }

    public String getFriendUserName() {
        return friendUserName;
    }

    public void setFriendUserName(String friendUserName) {
        this.friendUserName = friendUserName;
    }

    public String getFriendFirstName() {
        return friendFirstName;
    }

    public void setFriendFirstName(String friendFirstName) {
        this.friendFirstName = friendFirstName;
    }

    public String getFriendLastName() {
        return friendLastName;
    }

    public void setFriendLastName(String friendLastName) {
        this.friendLastName = friendLastName;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String toString() {
        return "Friend{" +
                "friendUserName='" + friendUserName + '\'' +
                ", friendFirstName='" + friendFirstName + '\'' +
                ", friendLastName='" + friendLastName + '\'' +
                ", friendEmail='" + friendEmail + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
