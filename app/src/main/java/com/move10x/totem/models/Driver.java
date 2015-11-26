package com.move10x.totem.models;

import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ravi on 10/11/2015.
 */
public class Driver{

    public static String DutyStatus_Available = "AVAILABLE";
    public static String DutyStatus_Offduty = "OFFDUTY";
    public static String DutyStatus_TOWARDS_TOPICKUP = "TOPICKUP";
    public static String DutyStatus_TOWARDS_LOADING = "LOADING";
    public static String DutyStatus_TOWARDS_DROP = "TOWARDS_DROP";
    public static String DutyStatus_UNLOADING = "UNLOADING";
    public static String DutyStatus_COMPLETE = "COMPLETE";

    public static String WorkStatus_Test = "TEST";
    public static String WorkStatus_Terminated = "TERMINATED";
    public static String WorkStatus_Active = "ACTIVE";
    public static String DutyStatus_Pending_Verify = "PENDING_VERIFY";
    public static String WorkStatus_Notice = "NOTICE";
    public static String DutyStatus_Training = "TRAINING";
    public static String DutyStatus_Outside = "OUTSIDE";

//    public static String booking_total = "Total Bookings";
//    public static String booking_today = "Today's Bookings";
//    public static String booking_weekly = "Weekly Bookings";
//    public static String booking_monthly = "Monthly Bookings";


    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private String userName;
    private String uId;
    private String imagePath = "http://icons.iconarchive.com/icons/iconshock/perspective-general/256/administrator-icon.png";
    private String tempoMake;
    private String tempoModel;
    private String category;
    private String regestrationNo;
    private String vinNo;
    private String dutyStatus;
    private String workStatus;
    private String imeiNo;
    private String MSISDN;
    private String address;
    private String vehicleLength;
    private String region = "Mumbai";
    private String baseStation = "Powai";
    private String phoneBrand;
    private String appVersion = "DL 12.0.2";
    private String AssignedMobile = "Honor Bee";
    private String rating;
    private String unsettledRun;
    private String Authority = "Driver";
    private String joinDate;
    private String updatedAt;
    private String gcmRegId;
    private String currentBookingReference;
    private String plan;
    private String vehicleBranded;
    private String SourcedBy;
    private String remarks;
    private String remarksTimeStamp;
    private double lattitude;
    private double longitude;

    public Driver(){

    }

    public Driver(String uId, String firstName, String lastName, String mobileNo, String dutyStatus, String workStatus,
                  double lattitude, double longitude, String category, String vehicleMake, String vehicleModel, String plan) {
        this.uId = uId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNo = mobileNo;
        this.workStatus = workStatus;
        this.dutyStatus = dutyStatus;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.category = category;
        this.tempoMake = vehicleMake;
        this.tempoModel = vehicleModel;
        this.plan = plan;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }


    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getVehicleLength() {
        return vehicleLength;
    }

    public void setVehicleLength(String vehicleLength) {
        this.vehicleLength = vehicleLength;
    }

    public String getTempoMake() {
        return tempoMake;
    }

    public void setTempoMake(String tempoMake) {
        this.tempoMake = tempoMake;
    }

    public String getTempoModel() {
        return tempoModel;
    }

    public void setTempoModel(String tempoModel) {
        this.tempoModel = tempoModel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRegestrationNo() {
        return regestrationNo;
    }

    public void setRegestrationNo(String regestrationNo) {
        this.regestrationNo = regestrationNo;
    }

    public String getVinNo() {
        return vinNo;
    }

    public void setVinNo(String vinNo) {
        this.vinNo = vinNo;
    }

    public String getDutyStatus() {
        return this.dutyStatus;
    }

    public void setDutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public String getMSISDN() {
        return MSISDN;
    }

    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBaseStation() {
        return baseStation;
    }

    public void setBaseStation(String baseStation) {
        this.baseStation = baseStation;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAssignedMobile() {
        return AssignedMobile;
    }

    public void setAssignedMobile(String assignedMobile) {
        AssignedMobile = assignedMobile;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUnsettledRun() {
        return unsettledRun;
    }

    public void setUnsettledRun(String unsettledRun) {
        this.unsettledRun = unsettledRun;
    }

    public String getAuthority() {
        return Authority;
    }

    public void setAuthority(String authority) {
        Authority = authority;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getGcmRegId() {
        return gcmRegId;
    }

    public void setGcmRegId(String gcmRegId) {
        this.gcmRegId = gcmRegId;
    }

    public String getCurrentBookingReference() {
        return currentBookingReference;
    }

    public void setCurrentBookingReference(String currentBookingReference) {
        this.currentBookingReference = currentBookingReference;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getVehicleBranded() {
        return vehicleBranded;
    }

    public void setVehicleBranded(String vehicleBranded) {
        this.vehicleBranded = vehicleBranded;
    }

    public String getSourcedBy() {
        return SourcedBy;
    }

    public void setSourcedBy(String sourcedBy) {
        SourcedBy = sourcedBy;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarksTimeStamp() {
        return remarksTimeStamp;
    }

    public void setRemarksTimeStamp(String remarksTimeStamp) {
        this.remarksTimeStamp = remarksTimeStamp;
    }

    public static Intent putDriverArguements(Intent intent, Driver driver){
        intent.putExtra("firstName", driver.getFirstName());
        intent.putExtra("lastName", driver.getLastName());
        intent.putExtra("email", driver.getEmail());
        intent.putExtra("mobileNo", driver.getMobileNo());
        intent.putExtra("userName", driver.getUserName());
        intent.putExtra("uId", driver.getuId());
        intent.putExtra("imagePath", driver.getImagePath());
        intent.putExtra("tempoMake", driver.getTempoMake());
        intent.putExtra("category", driver.getCategory());
        intent.putExtra("regestrationNo", driver.getRegestrationNo());
        intent.putExtra("vinNo", driver.getVinNo());
        intent.putExtra("dutyStatus", driver.getDutyStatus());
        intent.putExtra("workStatus", driver.getWorkStatus());
        intent.putExtra("imeiNo", driver.getImeiNo());
        intent.putExtra("MSISDN", driver.getMSISDN());
        intent.putExtra("address", driver.getAddress());
        intent.putExtra("region", driver.getRegion());
        intent.putExtra("baseStation", driver.getBaseStation());
        intent.putExtra("appVersion", driver.getAppVersion());
        intent.putExtra("AssignedMobile", driver.getAssignedMobile());
        intent.putExtra("rating", driver.getRating());
        intent.putExtra("unsettledRun", driver.getUnsettledRun());
        intent.putExtra("Authority", driver.getAuthority());
        intent.putExtra("joinDate", driver.getJoinDate());
        intent.putExtra("updatedAt", driver.getUpdatedAt());
        intent.putExtra("gcmRegId", driver.getGcmRegId());
        intent.putExtra("currentBookingReference", driver.getCurrentBookingReference());
        intent.putExtra("plan", driver.getPlan());
        intent.putExtra("vehicleBranded", driver.getVehicleBranded());
        intent.putExtra("SourcedBy", driver.getSourcedBy());
        intent.putExtra("lattitude", driver.getLattitude());
        intent.putExtra("longitude",driver.getLongitude());
        return intent;
    }

    public static Driver decodeJsonForMap(JSONObject jsonObject){
        try {
            Driver driver = new Driver();
            driver.uId = jsonObject.getString("uid");
            driver.firstName = jsonObject.getString("firstname");
            driver.lastName = jsonObject.getString("lastname");
            driver.mobileNo = jsonObject.getString("username");
            driver.email = jsonObject.getString("email");
            driver.userName = jsonObject.getString("username");
            driver.imagePath = jsonObject.getString("image_link");
            driver.tempoMake = jsonObject.getString("tmake");
            driver.tempoModel = jsonObject.getString("tmodel");
            driver.category = jsonObject.getString("tcat");
            driver.regestrationNo = jsonObject.getString("treg");
            driver.vinNo = jsonObject.getString("tvin");
            driver.dutyStatus = jsonObject.getString("dutyStatus");
            driver.workStatus = jsonObject.getString("workStatus");
            driver.imeiNo = jsonObject.getString("IMEI");
            driver.MSISDN = jsonObject.getString("MSISDN");
            driver.address = jsonObject.getString("address");
            driver.region = jsonObject.getString("region");
            driver.baseStation = jsonObject.getString("basestation");
            driver.appVersion = jsonObject.getString("appVersion");
            driver.phoneBrand = jsonObject.getString("pmodel");
            driver.rating = jsonObject.getString("avgRating");
            driver.unsettledRun = jsonObject.getString("unsettledRun");
            driver.joinDate = jsonObject.getString("joinDate");
            driver.updatedAt = jsonObject.getString("updatedAt");
            driver.gcmRegId = jsonObject.getString("gcm_regid");
            driver.currentBookingReference = jsonObject.getString("current_booking_ref");
            driver.plan = jsonObject.getString("plan");
            driver.vehicleBranded = jsonObject.getString("vehicleBranded");
            driver.SourcedBy = jsonObject.getString("sourced_by");
            driver.lattitude = Double.parseDouble(jsonObject.getString("lat"));
            driver.longitude = Double.parseDouble(jsonObject.getString("long"));
            return driver;
        } catch (JSONException ex) {
            //throw ex;
            Log.e("userModel", "Failed to decode json. " + ex.getMessage());
            return null;
        }
    }

    public static Driver decodeJsonForList(JSONObject jsonObject){
        try {

            Driver driver = new Driver();
            driver.uId = jsonObject.getString("uid");
            driver.firstName = jsonObject.getString("firstname");
            driver.lastName = jsonObject.getString("lastname");
            driver.mobileNo = jsonObject.getString("username");
            driver.email = jsonObject.getString("email");
            driver.userName = jsonObject.getString("username");
            driver.imagePath = jsonObject.getString("image_link");
            driver.tempoMake = jsonObject.getString("tmake");
            driver.tempoModel = jsonObject.getString("tmodel");
            driver.category = jsonObject.getString("tcat");
            driver.regestrationNo = jsonObject.getString("treg");
            driver.vinNo = jsonObject.getString("tvin");
            driver.dutyStatus = jsonObject.getString("dutyStatus");
            driver.workStatus = jsonObject.getString("workStatus");
            driver.imeiNo = jsonObject.getString("IMEI");
            driver.MSISDN = jsonObject.getString("MSISDN");
            driver.address = jsonObject.getString("address");
            driver.region = jsonObject.getString("region");
            driver.baseStation = jsonObject.getString("basestation");
            driver.appVersion = jsonObject.getString("appVersion");
            driver.phoneBrand = jsonObject.getString("pmodel");
            driver.rating = jsonObject.getString("avgRating");
            driver.unsettledRun = jsonObject.getString("unsettledRun");
            driver.joinDate = jsonObject.getString("joinDate");
            driver.updatedAt = jsonObject.getString("updatedAt");
            driver.gcmRegId = jsonObject.getString("gcm_regid");
            driver.currentBookingReference = jsonObject.getString("current_booking_ref");
            driver.plan = jsonObject.getString("plan");


            return driver;
        } catch (JSONException ex) {
            //throw ex;
            Log.e("userModel", "Failed to decode json. " + ex.getMessage());
            return null;
        }
    }

    public static Driver decodeJsonForDetails(JSONObject jsonObject){
        try {

            Driver driver = new Driver();
            driver.uId = jsonObject.getString("uid");
            driver.firstName = jsonObject.getString("firstname");
            driver.lastName = jsonObject.getString("lastname");
            driver.mobileNo = jsonObject.getString("username");
            driver.email = jsonObject.getString("email");
            driver.userName = jsonObject.getString("username");
            driver.imagePath = jsonObject.getString("image_link");
            driver.tempoMake = jsonObject.getString("tmake");
            driver.tempoModel = jsonObject.getString("tmodel");
            driver.category = jsonObject.getString("tcat");
            driver.regestrationNo = jsonObject.getString("treg");
            driver.vinNo = jsonObject.getString("tvin");
            driver.dutyStatus = jsonObject.getString("dutyStatus");
            driver.workStatus = jsonObject.getString("workStatus");
            driver.imeiNo = jsonObject.getString("IMEI");
            driver.MSISDN = jsonObject.getString("MSISDN");
            driver.address = jsonObject.getString("address");
            driver.region = jsonObject.getString("region");
            driver.baseStation = jsonObject.getString("basestation");
            driver.appVersion = jsonObject.getString("appVersion");
            driver.phoneBrand = jsonObject.getString("pmodel");
            driver.rating = jsonObject.getString("avgRating");
            driver.unsettledRun = jsonObject.getString("unsettledRun");
            driver.joinDate = jsonObject.getString("joinDate");
            driver.updatedAt = jsonObject.getString("updatedAt");
            driver.gcmRegId = jsonObject.getString("gcm_regid");
            driver.currentBookingReference = jsonObject.getString("current_booking_ref");
            driver.plan = jsonObject.getString("plan");
            driver.vehicleBranded = jsonObject.getString("vehicleBranded");
            driver.remarks = jsonObject.getString("remarks");
            driver.remarksTimeStamp = jsonObject.getString("remarksTimestamp");
            driver.SourcedBy = jsonObject.getString("sourced_by");
            return driver;
        } catch (JSONException ex) {
            //throw ex;
            Log.e("userModel", "Failed to decode json. " + ex.getMessage());
            return null;
        }
    }




    /**
     * Returns a string containing a concise, human-readable description of this
     * object. Subclasses are encouraged to override this method and provide an
     * implementation that takes into account the object's type and data. The
     * default implementation is equivalent to the following expression:
     * <pre>
     *   getClass().getName() + '@' + Integer.toHexString(hashCode())</pre>
     * <p>See <a href="{@docRoot}reference/java/lang/Object.html#writing_toString">Writing a useful
     * {@code toString} method</a>
     * if you intend implementing your own {@code toString} method.
     *
     * @return a printable representation of this object.
     */
    @Override
    public String toString() {

        String str = "";
        str += "FistName: " + (this.firstName != null ? this.firstName  : "''");
        str += ", ";
        str += "LastName: " + (this.lastName != null ? this.lastName  : "''");
        str += ", ";
        str += "email: " + (this.email != null ? this.email  : "''");
        str += ", ";
        str += "mobile no: " + (this.mobileNo!= null ? this.mobileNo  : "''");
        str += ", ";
        str += "username: " + (this.userName != null ? this.userName  : "''");
        str += ", ";
        str += "uid: " + (this.uId != null ? this.uId  : "''");
        str += ", ";
        str += "tempoMake: " + (this.tempoMake != null ? this.tempoMake  : "''");
        str += ", ";
        str += "tempoModel: " + (this.tempoModel != null ? this.tempoModel  : "''");
        str += ", ";
        str += "tempoCategory: " + (this.category != null ? this.category  : "''");
        str += ", ";
        str += "regNlo: " + (this.regestrationNo != null ? this.regestrationNo  : "''");
        str += ", ";
        str += "vinNo: " + (this.vinNo != null ? this.vinNo  : "''");
        str += ", ";
        str += "workStatus: " + (this.workStatus != null ? this.workStatus  : "''");
        str += ", ";
        str += "dutyStatus: " + (this.dutyStatus != null ? this.dutyStatus  : "''");
        str += ", ";
        str += "imei: " + (this.imeiNo != null ? this.imeiNo  : "''");
        str += ", ";
        str += "msisdn: " + (this.MSISDN != null ? this.MSISDN  : "''");
        str += ", ";
        str += "region: " + (this.region != null ? this.region  : "''");
        str += ", ";
        str += "basestation: " + (this.baseStation!= null ? this.baseStation  : "''");
        str += ", ";
        str += "phoneBrand: " + (this.phoneBrand != null ? this.phoneBrand  : "''");
        str += ", ";
        str += "appVersion: " + (this.appVersion != null ? this.appVersion  : "''");
        str += ", ";
        str += "AssibnedMobile: " + (this.AssignedMobile != null ? this.AssignedMobile  : "''");
        str += ", ";
        str += "rating: " + (this.rating != null ? this.rating  : "''");
        str += ", ";
        str += "UnsettledRun: " + (this.unsettledRun != null ? this.unsettledRun  : "''");
        str += ", ";
        str += "Authority: " + (this.getAuthority() != null ? this.Authority  : "''");
        str += ", ";
        str += "joinDate: " + (this.joinDate != null ? this.joinDate  : "''");
        str += ", ";
        str += "updatedAt: " + (this.updatedAt != null ? this.updatedAt  : "''");
        str += ", ";
        str += "gcmRegId: " + (this.gcmRegId != null ? this.gcmRegId  : "''");
        str += ", ";
        str += "currentBookingReference: " + (this.currentBookingReference != null ? this.currentBookingReference  : "''");
        str += ", ";
        str += "plan: " + (this.plan != null ? this.plan  : "''");
        str += ", ";
        str += "vehicleBranded: " + (this.vehicleBranded != null ? this.vehicleBranded  : "''");
        str += ", ";
        str += "SourcedBy: " + (this.SourcedBy != null ? this.SourcedBy  : "''");
        str += ", ";
        str += "lattitude: " + ( this.lattitude);
        str += ", ";
        str += "longitude: " + (this.longitude);
        return str;
    }
}
