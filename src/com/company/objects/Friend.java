package com.company.objects;

import java.util.ArrayList;
import java.util.Objects;

public class Friend {
    String friendUserName;
    String friendFirstName;
    String friendLastName;
    String friendEmail;
    String status;

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    String currentUserName;


    public Friend() {

    }

    public Friend(String friendUserName, String friendFirstName, String friendLastName, String friendEmail, String status, String currentUserName) {
        this.friendUserName = friendUserName;
        this.friendFirstName = friendFirstName;
        this.friendLastName = friendLastName;
        this.friendEmail = friendEmail;
        this.status = status;
        this.currentUserName = currentUserName;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend that = (Friend) o;
        return Objects.equals(friendUserName, that.friendUserName) &&
                Objects.equals(friendFirstName, that.friendFirstName) &&
                Objects.equals(friendLastName, that.friendLastName) &&
                Objects.equals(friendEmail, that.friendEmail) &&
                Objects.equals(status, that.status);
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
