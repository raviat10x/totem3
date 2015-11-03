package com.move10x.totem.models;

/**
 * Created by Ravi on 10/29/2015.
 */
public class Customer {
    private String id;
    private String uid;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String crm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    @Override
    public String toString() {
        String str = "";
        str += "id" + this.id != null ? this.id : "''";
        str += ", ";
        str += "uid" + this.uid != null ? this.uid : "''";
        str += ", ";
        str += "firstName" + this.firstName!= null ? this.firstName : "''";
        str += ", ";
        str += "lastName" + this.lastName != null ? this.lastName : "''";
        str += ", ";
        str += "mobile" + this.mobile != null ? this.mobile : "''";
        str += ", ";
        str += "email" + this.email != null ? this.email : "''";
        str += ", ";
        str += "crm" + this.crm != null ? this.crm : "''";
        return str;
    }
}

