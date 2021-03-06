package com.company.objects;


import com.company.exceptions.*;
import com.company.validations.UserValidations;

import javax.persistence.*;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import org.hibernate.annotations.Entity;
@Entity
@Table (name="USERS") //If you want to specify the field name in the database
public class User {



    @Id
    private String userName;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String userLocation;

    @Column(nullable = false)
    private String password;

    private ArrayList<String> activityLists = new ArrayList<>();
    //private List<UserFriend> userFriends = new ArrayList<>();
    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
   // @org.hibernate.annotations.ForeignKey(name="none")
    private Set<UserFriend> currentSet = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
   // @org.hibernate.annotations.ForeignKey(name="none")
    private Set<UserFriend> friendSet = new HashSet<>();


    public User(){

    }

    public User(String userName, String firstName, String lastName, String email){

        this.userName= userName;
        this.firstName= firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Set<UserFriend> getCurrentSet() {
        return currentSet;
    }

    public void setCurrentSet(Set<UserFriend> currentSet) {
        this.currentSet = currentSet;
    }

    public Set<UserFriend> getFriendSet() {
        return friendSet;
    }

    public void setFriendSet(Set<UserFriend> friendSet) {
        this.friendSet = friendSet;
    }

    public User(String userName, String firstName, String lastName, String email, String phoneNumber, String userLocation, String password, ArrayList<String> activityLists) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userLocation = userLocation;
        this.password = password;
        this.activityLists = activityLists;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) throws NotValidPassword {

        if (UserValidations.validPassword(password)) {
            this.password = password;
        }
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) throws NotValidUserName
    {
        if (UserValidations.validUserName(userName)) {
            this.userName = userName;
        }

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws NotValidFirstName {
        if (UserValidations.validFirstName(firstName)) {
            this.firstName = firstName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws NotValidLastName {
        if (UserValidations.validLastName(lastName)) {
            this.lastName = lastName;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws NotValidEmail {

        if (UserValidations.validEmail(email)) {
            this.email = email;
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws NotValidPhone {
        if (UserValidations.validPhone(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        }
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) throws NotValidLocation{
        if (UserValidations.validLocation(userLocation)) {
            this.userLocation = userLocation;
        }
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
            activityLists.add(activityName);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"userName\":\"" + userName + '\"' +
                ", \"firstName\":\"" + firstName + '\"' +
                ", \"lastName\":\"" + lastName + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"phoneNumber\":\"" + phoneNumber + '\"' +
                ", \"userLocation\":\"" + userLocation + '\"' +
                ", \"password\":\"" + password + '\"' +
                ", \"activityLists\":\"" + activityLists + '\"' +
                '}';
    }
}