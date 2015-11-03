package com.move10x.totem.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ravi on 10/9/2015.
 * This user model represents an crm or vrm.
 */
public class User {

    public String UId;
    public String UserName;
    public String FirstName;
    public String LastName;
    public String Password;
    public String EmailId;
    public String PhoneNo;
    public String Department;
    public String Role;
    public String ImagePath;


    public static User decodeJson(JSONObject jsonObject) {
        try {
            User user = new User();
            user.UId = jsonObject.getString("uid");
            user.UserName = jsonObject.getString("uname");
            user.FirstName = jsonObject.getString("fname");
            user.LastName = jsonObject.getString("lname");
            user.EmailId = jsonObject.getString("email");
            user.PhoneNo = jsonObject.getString("mobileNo");
            user.Department = jsonObject.getString("department");
            user.Role = jsonObject.getString("role");
            user.ImagePath = jsonObject.getString("image_link");
            return user;
        } catch (JSONException ex) {
            //throw ex;
            Log.e("userModel", "Failed to decode json. " + ex.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        String str = "";
        str += this.UId != null ? this.UId : "''";
        str += ", ";
        str += this.UserName != null ? this.UserName : "''";
        str += ", ";
        str += this.FirstName != null ? this.FirstName : "''";
        str += ", ";
        str += this.LastName != null ? this.LastName : "''";
        str += ", ";
        str += this.EmailId != null ? this.EmailId : "''";
        str += ", ";
        str += this.PhoneNo != null ? this.PhoneNo : "''";
        str += ", ";
        str += this.Department != null ? this.Department : "''";
        str += ", ";
        str += this.Role != null ? this.Role : "''";
        str += ", ";
        str += this.ImagePath != null ? this.ImagePath : "''";
        return str;
    }
}

