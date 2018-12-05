package com.company.objects;
//import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import com.company.managers.Manager;
import org.hibernate.Session;
//import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.*;
//import javax.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class UserFriend {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn( name="friendUserName", referencedColumnName = "username" )
    // foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
    private User friendUser;

    @ManyToOne
    @JoinColumn(name="currentUserName", referencedColumnName = "username")
    private User currentUser;

    private String status;

//    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    //  @Cascade({org.hibernate.annotations.CascadeType.ALL, })
//    // @JoinColumn (Fetch = FetchType.EAGER)
//    private List<User> user = new ArrayList<>();

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
//    public List<UserFriend> getResultList(Session session){
//        List<UserFriend> list = session
//                .createQuery("from UserFriend ")
//                .getResultList();
//        return list;
//    }





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
