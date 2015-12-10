package com.move10x.totem.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Ravi on 10/29/2015.
 */
public class Customer extends ArrayList<Customer> {
    private static final String TAG = "Customer Model";
    private String customerUid;
    private String uid;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String crm;
    private String landline;
    private String address;
    private String dob;
    private String annivarsary;
    private String fvrtVehicle;
    private String area;
    private String city;
    private String pin;
    private String billName;
    private String vCardImage;
    private String uniqueId;
    private String customerLocation;
    private String companyName;
    private String avgTripCost;
    private String goodsType;
    private String weeklyRequirement;
    private String businessType;

    public Customer() {
    }


    public Customer(String customerUid, String uid, String firstName, String lastName, String mobile, String email, String crm, String landline, String address, String dob, String annivarsary, String fvrtVehicle, String area, String city, String pin, String billName, String vCardImage, String uniqueId, String customerLocation, String companyName, String avgTripCost, String goodsType,  String weeklyRequirement, String businessType) {
        this.customerUid = customerUid;
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.crm = crm;
        this.landline = landline;
        this.address = address;
        this.dob = dob;
        this.annivarsary = annivarsary;
        this.fvrtVehicle = fvrtVehicle;
        this.area = area;
        this.city = city;
        this.pin = pin;
        this.billName = billName;
        this.vCardImage = vCardImage;
        this.uniqueId = uniqueId;
        this.customerLocation = customerLocation;
        this.companyName = companyName;
        this.goodsType = goodsType;
        this.businessType = businessType;
        this.weeklyRequirement = weeklyRequirement;
        this.avgTripCost = avgTripCost;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getWeeklyRequirement() {
        return weeklyRequirement;
    }

    public void setWeeklyRequirement(String weeklyRequirement) {
        this.weeklyRequirement = weeklyRequirement;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getAvgTripCost() {
        return avgTripCost;
    }

    public void setAvgTripCost(String avgTripCost) {
        this.avgTripCost = avgTripCost;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
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

    public String getlandline() {
        return landline;
    }

    public void setlandline(String landline) {
        this.landline = landline;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAnnivarsary() {
        return annivarsary;
    }

    public void setAnnivarsary(String annivarsary) {
        this.annivarsary = annivarsary;
    }

    public String getFvrtVehicle() {
        return fvrtVehicle;
    }

    public void setFvrtVehicle(String fvrtVehicle) {
        this.fvrtVehicle = fvrtVehicle;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getvCardImage() {
        return vCardImage;
    }

    public void setvCardImage(String vCardImage) {
        this.vCardImage = vCardImage;
    }

    public String getCustomerLocation() {
        return customerLocation;
    }

    public void setCustomerLocation(String customerLocation) {
        this.customerLocation = customerLocation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "customerUid='" + customerUid + '\'' +
                ", uid='" + uid + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", crm='" + crm + '\'' +
                ", landline='" + landline + '\'' +
                ", address='" + address + '\'' +
                ", dob='" + dob + '\'' +
                ", annivarsary='" + annivarsary + '\'' +
                ", fvrtVehicle='" + fvrtVehicle + '\'' +
                ", area='" + area + '\'' +
                ", city='" + city + '\'' +
                ", pin='" + pin + '\'' +
                ", billName='" + billName + '\'' +
                ", vCardImage='" + vCardImage + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                ", customerLocation='" + customerLocation + '\'' +
                ", companyName='" + companyName + '\'' +
                ", avg_trip_cost='" + avgTripCost + '\'' +
                ", goods_type='" + goodsType + '\'' +
                ", weekly_requirement='" + weeklyRequirement + '\'' +
                ", business_type='" + businessType + '\'' +
                '}';
    }

    public static Customer decodeJsonForList(JSONObject jsonObject) {
        try {
            Log.i("Before Decoding", "-----------------------" + jsonObject);

            Log.d(TAG, "decodingCustomerlist" );
            Customer customer = new Customer();
            customer.uid = jsonObject.getString("uid");
            customer.firstName = jsonObject.getString("firstname");
            customer.lastName = jsonObject.getString("lastname");
            Log.d(TAG, "First and Last Name : " + customer.firstName);
            customer.mobile = jsonObject.getString("username");
            customer.email = jsonObject.getString("email");
            customer.dob = jsonObject.getString("dob");
            customer.annivarsary = jsonObject.getString("anniversary");
            customer.fvrtVehicle = jsonObject.getString("favVehicle");
            customer.landline = jsonObject.getString("landline");
            customer.billName = jsonObject.getString("billname");
            customer.address = jsonObject.getString("address");
            customer.area = jsonObject.getString("area");
            customer.city = jsonObject.getString("city");
            customer.pin = jsonObject.getString("pin");
            customer.uniqueId = jsonObject.getString("unique_id");
//            customer.customerLocation = jsonObject.getString("currentLocation");
            customer.companyName = jsonObject.getString("business");
            customer.avgTripCost  = jsonObject.getString("avg_trip_cost");
            customer.weeklyRequirement = jsonObject.getString("weekly_requirement");
            customer.goodsType = jsonObject.getString("goods_type");
            customer.businessType = jsonObject.getString("business_type");


            Log.i(TAG,"----------------------------------------------- Check Values"+customer.avgTripCost+" \n "+customer.weeklyRequirement+" \n "+customer.goodsType+" \n "+customer.businessType);
            return customer;
        } catch (JSONException ex) {
            //throw ex;
            Log.e("userModel", "Failed to decode json. " + ex.getMessage());
            return null;
        }
    }
    
    

    public static Customer decodeJsonForDetails(JSONObject jsonObject){
        try {


            Log.i("Before Decoding", "-----------------------" + jsonObject);
            Customer customer = new Customer();
            customer.uid = jsonObject.getString("uid");
            customer.firstName = jsonObject.getString("firstname");
            customer.lastName = jsonObject.getString("lastname");
            customer.mobile = jsonObject.getString("username");
            customer.email = jsonObject.getString("email");
            customer.area = jsonObject.getString("area");
            customer.city = jsonObject.getString("city");
            customer.pin = jsonObject.getString("pin");
            customer.address = jsonObject.getString("address");
            customer.landline = jsonObject.getString("landline");
            customer.dob = jsonObject.getString("dob");
            customer.annivarsary = jsonObject.getString("anniversary");
            customer.fvrtVehicle = jsonObject.getString("favVehicle");
            customer.billName = jsonObject.getString("billname");
            customer.uniqueId = jsonObject.getString("unique_id");
//            customer.customerLocation = jsonObject.getString("currentLocation");
            customer.vCardImage = jsonObject.getString("vCardImg");
            customer.companyName = jsonObject.getString("business");
            customer.avgTripCost = jsonObject.getString("avg_trip_cost");
            customer.weeklyRequirement = jsonObject.getString("weekly_requirement");
            customer.goodsType = jsonObject.getString("goods_type");
            customer.businessType = jsonObject.getString("business_type");
            Log.i(TAG,"-----------------------------------------------Check Details"+customer.avgTripCost+" \n "+customer.businessType);
            return customer;
        } catch (JSONException ex) {
            //throw ex;
            Log.e("userModel", "Failed to decode json. " + ex.getMessage());
            return null;
        }
    }

}



