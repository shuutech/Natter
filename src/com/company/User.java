package com.company;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table (name="USERS") //If you want to specify the field name in the database
public class User {



    @Id
    private String userName;

    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String email;
    private String phoneNumber;
    private String userLocation;
    private String password;



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {


        this.password = password;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName){

        this.userName = userName;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception{

        if (email.isEmpty()){
            throw new Exception();
        }

        this.email = email;

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }


    public String toString() {
        return "User{" +

                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userLocation='" + userLocation + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}