package com.company;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class UserFriend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="friendUserName", referencedColumnName = "username")
    private User friendUser;

    @ManyToOne
    @JoinColumn(name="currentUserName", referencedColumnName = "username")
    private User currentUser;

    private String status;

    private ArrayList<String> activityLists;

    public User getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(User friend) {
        this.friendUser = friend;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User current) {
        this.currentUser = current;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserFriend() {

    }

    public ArrayList<String> getActivityLists() {

        return activityLists;
    }

    public void setActivityLists(ArrayList<String> activityLists) {

        this.activityLists = activityLists;
    }
    public void addActivity(String activityName){

        try{activityLists.add(activityName);}
        catch(Exception e){
            activityLists = new ArrayList<String>();
        }
            activityLists.add(activityName);
    }

    public static void main(String[] args) {

        new UserFriend().addActivity("test");
    }

    public String toString() {
        return "UserFriend{" +
                "id=" + id +
                ", friendUser=" + friendUser +
                ", currentUser=" + currentUser +
                ", status='" + status + '\'' +
                ", activityList=" + activityLists +
                '}';
    }
}
