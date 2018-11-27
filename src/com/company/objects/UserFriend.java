package com.company.objects;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
public class UserFriend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="friendUserName", referencedColumnName = "username")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User friendUser;

    @ManyToOne
    @JoinColumn(name="currentUserName", referencedColumnName = "username")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User currentUser;

    private String status;

    //private ArrayList<String> activityLists;

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



    public String toString() {
        return "UserFriend{" +
                "id=" + id +
                ", friendUser=" + friendUser +
                ", currentUser=" + currentUser +
                ", status='" + status + '\'' +
            //    ", activityList=" + activityLists +
                '}';
    }
}
