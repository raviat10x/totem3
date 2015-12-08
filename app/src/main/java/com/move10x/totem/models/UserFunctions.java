//package com.move10x.totem.models;
//
//import android.content.Context;
//import android.util.Log;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by aakash on 20/05/15.
// */
//public class UserFunctions {
//
//    private JSONParser jsonParser;
//
//    //URL of the PHP API
//    @NotNull
//    private static String loginURL = "http://www.move10x.com/webservice/";
//    @NotNull
//    private static String gcmURL = "http://www.move10x.com/webservice/gcm.php";
//    @NotNull
//    private static String acceptBookingURL = "http://www.move10x.com/webservice/";
//    @NotNull
//    private static String rejectBookingURL = "http://www.move10x.com/webservice/";
//    @NotNull
//    private static String updateBookingURL = "http://www.move10x.com/webservice/";
//    @NotNull
//    private static String completeBookingURL = "http://www.move10x.com/webservice/";
//    @NotNull
//    private static String getDriverPastBookingsURL = "http://www.move10x.com/webservice/";
//    @NotNull
//    private static String updateReceiverSignatureURL = "http://www.move10x.com/webservice/";
//    @NotNull
//    private static String updateGoodsImageLinkURL = "http://www.move10x.com/webservice/";
//    @NotNull
//    private static String canceledBookingURL = "http://www.move10x.com/webservice/";
//    @NotNull
//    private static String reassignedBookingURL = "http://www.move10x.com/webservice/";
//    @NotNull
//    private static String sendStatusPingURL = "http://www.move10x.com/webservice/";
//    private static String uploadimageURL = "http://www.move10x.com/webservice/";
//
//    private static String gcmAcknowledgeURL = "http://www.move10x.com/webservice/";
//    private static String getBookingFareURL = "http://www.move10x.com/webservice/";
//
//    @NotNull
//    private static String loginDriver_tag = "loginDriver";
//    @NotNull
//    private static String updateDriverStatus_tag = "updateDriverStatus";
//    @NotNull
//    private static String sendGcmRegId_tag = "sendGcmRegId";
//    @NotNull
//    private static String acceptBooking_tag = "acceptBooking";
//    @NotNull
//    private static String rejectBooking_tag = "rejectBooking";
//    @NotNull
//    private static String bookingUpdate_tag = "updateBookingStage";
//    @NotNull
//    private static String bookingComplete_tag = "completeBooking";
//    @NotNull
//    private static String getDriverPastBookings_tag = "getDriverBookings";
//    @NotNull
//    private static String updateReceiverSignature_tag = "updateReceiverSignatureLink";
//    @NotNull
//    private static String updateGoodsImageLink_tag = "updateGoodsImageLink";
//    @NotNull
//    private static String bookingCanceled_tag = "canceledBooking";
//    @NotNull
//    private static String bookingReassigned_tag = "reassignedBooking";
//    @NotNull
//    private static String sendStatusPing_tag = "statusPing";
//
//    private static String mapSnapshot_tag = "mapsnapshot";
//
//    private static String gcmAcknowledge_tag = "pushBack";
//    private static String getBookingFare_tag = "getBookingFare";
//
//    private static String GOOGLE_API_SERVER_KEY = "AIzaSyBIzxm_Wb-MoX82lBgqRjVHd1UWHqJqb_8";
//
//    // constructor
//    public UserFunctions(){
//        jsonParser = new JSONParser();
//    }
//
//    /**
//     * Function to Login
//     **/
//
//    @Nullable
//    public JSONObject loginDriver(String IMEI){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", loginDriver_tag));
//        params.add(new BasicNameValuePair("IMEI", IMEI));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
//        return json;
//    }
//
//    /**
//     * Function to logout user
//     * Resets the temporary data stored in SQLite Database
//     * */
//    public boolean logoutUser(Context context){
//        @NotNull DatabaseHandler db = new DatabaseHandler(context);
//        db.resetTables();
//        return true;
//    }
//
//    /**
//     * Function to Reset bookings for a driver
//     * Resets the temporary data stored in SQLite Database
//     * */
//    public boolean resetBookingTable(Context context){
//        @NotNull DatabaseHandler db = new DatabaseHandler(context);
//        db.resetBookingTable();
//        return true;
//    }
//
//    /**
//     * Function to Reset current active booking for this driver
//     * Resets the temporary data stored in SQLite Database
//     * */
//    public boolean clearCurrentBooking(Context context){
//        @NotNull DatabaseHandler db = new DatabaseHandler(context);
//        db.resetCurrentBookingPersist();
//        return true;
//    }
//
//    @Nullable
//    public JSONObject updateDriverStatus(String IMEI, String currentStatus){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", updateDriverStatus_tag));
//        params.add(new BasicNameValuePair("IMEI", IMEI));
//        params.add(new BasicNameValuePair("status", currentStatus));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
//        return json;
//    }
//
//    @Nullable
//    public JSONObject sendStatusPing(String IMEI, String batteryPct, String dutyStatus, String timestamp, int day, String versionName, String gcmRegid){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", sendStatusPing_tag));
//        params.add(new BasicNameValuePair("IMEI", IMEI));
//        params.add(new BasicNameValuePair("batteryPct", batteryPct));
//        params.add(new BasicNameValuePair("dutyStatus", dutyStatus));
//        params.add(new BasicNameValuePair("timestamp", timestamp));
//        params.add(new BasicNameValuePair("day", String.valueOf(day)));
//        params.add(new BasicNameValuePair("versionName", versionName));
//        params.add(new BasicNameValuePair("gcmRegid", gcmRegid));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(sendStatusPingURL, params);
//        return json;
//    }
//
//    @Nullable
//    public JSONObject sendGcmRegId(String token, String driverUid){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", sendGcmRegId_tag));
//        params.add(new BasicNameValuePair("regId", token));
//        params.add(new BasicNameValuePair("driverUid", driverUid));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(gcmURL, params);
//        return json;
//    }
//
//    @Nullable
//    public JSONObject acceptBooking(String driverUid, String booking_ref){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", acceptBooking_tag));
//        params.add(new BasicNameValuePair("driverUid", driverUid));
//        params.add(new BasicNameValuePair("booking_ref", booking_ref));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(acceptBookingURL, params);
//        return json;
//    }
//
//    @Nullable
//    public JSONObject rejectBooking(String driverUid, String booking_ref, String rejectReason){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", rejectBooking_tag));
//        params.add(new BasicNameValuePair("driverUid", driverUid));
//        params.add(new BasicNameValuePair("booking_ref", booking_ref));
//        params.add(new BasicNameValuePair("rejectReason", rejectReason));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(rejectBookingURL, params);
//        return json;
//    }
//
//    @Nullable
//    public JSONObject sendBookingUpdate(String updatedStage, String param1, String param2, String driverUid, String booking_ref){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", bookingUpdate_tag));
//        params.add(new BasicNameValuePair("updatedStage", updatedStage));
//        params.add(new BasicNameValuePair("driverUid", driverUid));
//        params.add(new BasicNameValuePair("booking_ref", booking_ref));
//        params.add(new BasicNameValuePair("param1", param1));
//        params.add(new BasicNameValuePair("param2", param2));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(updateBookingURL, params);
//        return json;
//    }
//
//    @Nullable
//    public JSONObject sendBookingComplete(String driverUid, String booking_ref, long totalFare, long totalDriverShare, String totalJourneyTime, long totalWaitingTime, String totalDistanceKm, String chargeableDistanceKm, String pickedupTime, String droppedTime, Double totalDiscount, String paidToDriver, String recordedDrops){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", bookingComplete_tag));
//        params.add(new BasicNameValuePair("totalFare", String.valueOf(totalFare)));
//        params.add(new BasicNameValuePair("totalDriverShare", String.valueOf(totalDriverShare)));
//        params.add(new BasicNameValuePair("driverUid", driverUid));
//        params.add(new BasicNameValuePair("booking_ref", booking_ref));
//        params.add(new BasicNameValuePair("totalJourneyTime", totalJourneyTime));
//        params.add(new BasicNameValuePair("totalWaitingTime", String.valueOf(totalWaitingTime)));
//        params.add(new BasicNameValuePair("totalDistanceKm", totalDistanceKm));
//        params.add(new BasicNameValuePair("chargeableDistanceKm", chargeableDistanceKm));
//        params.add(new BasicNameValuePair("pickedupTime", pickedupTime));
//        params.add(new BasicNameValuePair("droppedTime", droppedTime));
//        params.add(new BasicNameValuePair("totalDiscount", String.valueOf(totalDiscount)));
//        params.add(new BasicNameValuePair("paidToDriver", String.valueOf(paidToDriver)));
//        params.add(new BasicNameValuePair("recordedDrops", recordedDrops));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(completeBookingURL, params);
//        return json;
//    }
//
//    @Nullable
//    public JSONObject sendBookingCanceled(String driverUid, String booking_ref, long totalFare, long totalDriverShare, String totalJourneyTime, long totalWaitingTime, String totalDistanceKm, String chargeableDistanceKm, String pickedupTime, String droppedTime, String canceledBy, String paidToDriver, String recordedDrops){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", bookingCanceled_tag));
//        params.add(new BasicNameValuePair("totalFare", String.valueOf(totalFare)));
//        params.add(new BasicNameValuePair("totalDriverShare", String.valueOf(totalDriverShare)));
//        params.add(new BasicNameValuePair("driverUid", driverUid));
//        params.add(new BasicNameValuePair("booking_ref", booking_ref));
//        params.add(new BasicNameValuePair("totalJourneyTime", totalJourneyTime));
//        params.add(new BasicNameValuePair("totalWaitingTime", String.valueOf(totalWaitingTime)));
//        params.add(new BasicNameValuePair("totalDistanceKm", totalDistanceKm));
//        params.add(new BasicNameValuePair("chargeableDistanceKm", chargeableDistanceKm));
//        params.add(new BasicNameValuePair("pickedupTime", pickedupTime));
//        params.add(new BasicNameValuePair("droppedTime", droppedTime));
//        params.add(new BasicNameValuePair("canceledBy", canceledBy));
//        params.add(new BasicNameValuePair("paidToDriver", String.valueOf(paidToDriver)));
//        params.add(new BasicNameValuePair("recordedDrops", recordedDrops));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(canceledBookingURL, params);
//        return json;
//    }
//
//    @Nullable
//    public JSONObject getBookingFare(String driverUid, String booking_ref, String totalJourneyTime, String totalWaitingTime, String chargeableDistanceKm, String recordedDrops, String promoCode, String vehicleType, String status, String stage, String plan){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", getBookingFare_tag));
//        params.add(new BasicNameValuePair("driver_uid", driverUid));
//        params.add(new BasicNameValuePair("booking_ref", booking_ref));
//        params.add(new BasicNameValuePair("total_time", totalJourneyTime));
//        params.add(new BasicNameValuePair("total_waiting_time", totalWaitingTime));
//        params.add(new BasicNameValuePair("distance", chargeableDistanceKm));
//        params.add(new BasicNameValuePair("drops", recordedDrops));
//        params.add(new BasicNameValuePair("promo_code", promoCode));
//        params.add(new BasicNameValuePair("vehicle_type", vehicleType));
//        params.add(new BasicNameValuePair("status", status));
//        params.add(new BasicNameValuePair("stage", stage));
//        params.add(new BasicNameValuePair("plan", plan));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(getBookingFareURL, params);
//        return json;
//    }
//
//    @Nullable
//    public JSONObject sendBookingReassigned(String driverUid, String booking_ref, long totalFare, long totalDriverShare, String totalJourneyTime, long totalWaitingTime, String totalDistanceKm, String chargeableDistanceKm, String pickedupTime, String droppedTime, String canceledBy){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", bookingReassigned_tag));
//        params.add(new BasicNameValuePair("totalFare", String.valueOf(totalFare)));
//        params.add(new BasicNameValuePair("totalDriverShare", String.valueOf(totalDriverShare)));
//        params.add(new BasicNameValuePair("driverUid", driverUid));
//        params.add(new BasicNameValuePair("booking_ref", booking_ref));
//        params.add(new BasicNameValuePair("totalJourneyTime", totalJourneyTime));
//        params.add(new BasicNameValuePair("totalWaitingTime", String.valueOf(totalWaitingTime)));
//        params.add(new BasicNameValuePair("totalDistanceKm", totalDistanceKm));
//        params.add(new BasicNameValuePair("chargeableDistanceKm", chargeableDistanceKm));
//        params.add(new BasicNameValuePair("pickedupTime", pickedupTime));
//        params.add(new BasicNameValuePair("droppedTime", droppedTime));
//        params.add(new BasicNameValuePair("canceledBy", canceledBy));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(reassignedBookingURL, params);
//        return json;
//    }
//
//    @Nullable
//    public JSONObject getGmapsDirections(Double sourceLat, Double sourceLng, Double desLat, Double desLng){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        @NotNull StringBuilder urlString = new StringBuilder();
//        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
//        urlString.append("?origin="); // from
//        urlString.append(Double.toString(sourceLat));
//        urlString.append(",");
//        urlString.append(Double.toString(sourceLng));
//        urlString.append("&destination=");// to
//        urlString.append(Double.toString(desLat));
//        urlString.append(",");
//        urlString.append(Double.toString(desLng));
//        urlString.append("&sensor=false&mode=driving&alternatives=true");
//        urlString.append("&key=" + GOOGLE_API_SERVER_KEY);
//
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(urlString.toString(), params);
//        Log.i("", "MAPS Paramter : " + json);
//        return json;
//    }
//
//    @Nullable
//    public JSONObject getPastBookings(String driverUid){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", getDriverPastBookings_tag));
//        params.add(new BasicNameValuePair("driverUid", driverUid));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(getDriverPastBookingsURL, params);
//        return json;
//    }
//
//    @Nullable
//    public JSONObject updateReceiverSignature(String booking_ref, String fileUrl){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", updateReceiverSignature_tag));
//        params.add(new BasicNameValuePair("booking_ref", booking_ref));
//        params.add(new BasicNameValuePair("fileUrl", fileUrl));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(updateReceiverSignatureURL, params);
//        return json;
//    }
//
//    @Nullable
//    public JSONObject updateGoodsImageLink(String booking_ref, String fileUrl){
//        // Building Parameters
//        @NotNull List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", updateGoodsImageLink_tag));
//        params.add(new BasicNameValuePair("booking_ref", booking_ref));
//        params.add(new BasicNameValuePair("fileUrl", fileUrl));
//        @Nullable JSONObject json = jsonParser.getJSONFromUrl(updateGoodsImageLinkURL, params);
//        return json;
//    }
//
//    public JSONObject sendMapSnapshot(String encodedImage){
//        // Building Parameters
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", mapSnapshot_tag));
//        params.add(new BasicNameValuePair("encodedImage", encodedImage));
//        JSONObject json = jsonParser.getJSONFromUrl(uploadimageURL,params);
//        return json;
//    }
//
//    public JSONObject acknowledgeGCM(String gcmRegid, String booking_ref, String driverUid) {
//        // Building Parameters
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("tag", gcmAcknowledge_tag));
//        params.add(new BasicNameValuePair("driverRegId", gcmRegid));
//        params.add(new BasicNameValuePair("booking_ref", booking_ref));
//        params.add(new BasicNameValuePair("driverUid", driverUid));
//        JSONObject json = jsonParser.getJSONFromUrl(gcmAcknowledgeURL,params);
//        return json;
//    }
//}
