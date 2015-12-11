package com.move10x.totem.models;

import android.util.Log;

/**
 * Created by Ravi on 10/9/2015.
 */
public class CurrentProfile {

    //private variables
    private String userId = null;
    private String userName = null;
    public String firstName = null;
    public String lastName = null;
    private String imageUrl = null;
    private String phoneNumber = null;
    private String userType = null; //CRM or VRM

    public CurrentProfile(String userId, String firstName, String lastName, String userName, String phoneNumber, String userType, String imageUrl) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    @Override
    public String toString() {
        String str = "";
        Log.d("login", "userid: " + this.userId);
        str += "userID: " + this.userId != null ? this.userId : "''";
        str += ", ";
        str += "firstName: " + this.firstName != null ? this.firstName: "''";
        str += ", ";
        str += this.lastName != null ? this.lastName : "''";
        str += ", ";
        str += "userName: " + this.userName != null ? this.userName : "''";
        str += ", ";
        str += "mobile: " + this.phoneNumber != null ? this.phoneNumber : "''";
        str += ", ";
        str += "userType: " + this.userType != null ? this.userType : "''";
        str += ", ";
        str += "imageUrl:" + this.imageUrl != null ? this.imageUrl : "''";
        str += ", ";
        return str;
    }
}

