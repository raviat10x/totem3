package com.move10x.totem.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ravi on 10/21/2015.
 */
public class Booking {

    private String reference;
    private String bookedBy;
    private String source;
    private String vehicleCategory;
    private String tempoBrand;
    private String status;
    private String date;
    private String time;
    private String pickup;
    private String drop;
    private String totalJourneyTime;
    private String totalWaitingTime;
    private String totalDistance;
    private String chargeableDistance;
    private String totalFare;
    private String discount;
    private String driverFareShare;
    private String paidToCompany;
    private String paidToDriver;
    private String driverRating;
    private String customerRating;
    private String bookingCreatedAt;
    private Customer customer;

    public Booking() {

    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }

    public String getChargeableDistance() {
        return chargeableDistance;
    }

    public void setChargeableDistance(String chargeableDistance) {
        this.chargeableDistance = chargeableDistance;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(String vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }

    public String getTempoBrand() {
        return tempoBrand;
    }

    public void setTempoBrand(String tempoBrand) {
        this.tempoBrand = tempoBrand;
    }

    public String getTotalJourneyTime() {
        return totalJourneyTime;
    }

    public void setTotalJourneyTime(String totalJourneyTime) {
        this.totalJourneyTime = totalJourneyTime;
    }

    public String getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public void setTotalWaitingTime(String totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDriverFareShare() {
        return driverFareShare;
    }

    public void setDriverFareShare(String driverFareShare) {
        this.driverFareShare = driverFareShare;
    }

    public String getPaidToCompany() {
        return paidToCompany;
    }

    public void setPaidToCompany(String paidToCompany) {
        this.paidToCompany = paidToCompany;
    }

    public String getPaidToDriver() {
        return paidToDriver;
    }

    public void setPaidToDriver(String paidToDriver) {
        this.paidToDriver = paidToDriver;
    }

    public String getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(String driverRating) {
        this.driverRating = driverRating;
    }

    public String getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(String customerRating) {
        this.customerRating = customerRating;
    }

    public String getBookingCreatedAt() {
        return bookingCreatedAt;
    }

    public void setBookingCreatedAt(String bookingCreatedAt) {
        this.bookingCreatedAt = bookingCreatedAt;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public static Booking decodeBookingListResponseJson(JSONObject jsonObject) throws JSONException {
        Booking booking = new Booking();
        booking.setReference(jsonObject.getString("booking_ref"));
        booking.setStatus(jsonObject.getString("booking_status"));
        booking.setDate(jsonObject.getString("booking_date"));
        booking.setTime(jsonObject.getString("booking_time"));
        booking.setPickup(jsonObject.getString("booking_pickup"));
        booking.setDrop(jsonObject.getString("booking_drop"));
        booking.setTotalFare(jsonObject.getString("booking_cost"));
        booking.setTempoBrand(jsonObject.getString("booking_tempo"));
        booking.setDriverFareShare(jsonObject.getString("booking_driverShare"));
        booking.setChargeableDistance(jsonObject.getString("booking_chargeableDistance"));
        booking.setTotalDistance(jsonObject.getString("booking_totalDistance"));
        return booking;
    }


    public static Booking decodeBookingDetailsResponseJson(JSONObject jsonObject) throws JSONException {
        Booking booking = new Booking();
        booking.setBookedBy(jsonObject.has("booking_by") ? jsonObject.getString("booking_by") : null);
        booking.setReference(jsonObject.has("booking_ref") ? jsonObject.getString("booking_ref") : null);
        booking.setSource(jsonObject.has("booking_source")?jsonObject.getString("booking_source" ) : null);
        booking.setStatus(jsonObject.has("booking_status")?jsonObject.getString("booking_status" ) : null);
        booking.setDate(jsonObject.has("booking_date")?jsonObject.getString("booking_date" ) : null);
        booking.setTime(jsonObject.has("booking_time")?jsonObject.getString("booking_time" ) : null);
        booking.setPickup(jsonObject.has("pickup_address")?jsonObject.getString("pickup_address" ) : null);
        booking.setDrop(jsonObject.has("dest_address")?jsonObject.getString("dest_address" ) : null);
        booking.setVehicleCategory(jsonObject.has("tempo_type")?jsonObject.getString("tempo_type" ) : null);
        booking.setTotalJourneyTime(jsonObject.has("total_journey_time")?jsonObject.getString("total_journey_time" ) : null);
        booking.setTotalWaitingTime(jsonObject.has("")?jsonObject.getString("total_waiting_time" ) : null);
        booking.setTotalDistance(jsonObject.has("total_waiting_time")?jsonObject.getString("total_distance_km" ) : null);
        booking.setTotalFare(jsonObject.has("total_fare")?jsonObject.getString("total_fare" ) : null);
        booking.setDiscount(jsonObject.has("total_discount")?jsonObject.getString("total_discount" ) : null);
        booking.setDriverFareShare(jsonObject.has("driver_fare_share")?jsonObject.getString("driver_fare_share" ) : null);
        booking.setPaidToDriver(jsonObject.has("paid_to_driver")?jsonObject.getString("paid_to_driver" ) : null);
        booking.setPaidToCompany(jsonObject.has("paid_to_company")?jsonObject.getString("paid_to_company" ) : null);
        booking.setDriverRating(jsonObject.has("driver_rating")?jsonObject.getString("driver_rating" ) : null);
        booking.setCustomerRating(jsonObject.has("customer_rating")?jsonObject.getString("customer_rating" ) : null);
        booking.setBookingCreatedAt(jsonObject.has("created_at")?jsonObject.getString("created_at" ) : null);

        //Set customer.
        Customer cust = new Customer();
        JSONObject rawCustomer = jsonObject.getJSONObject("customerDetails");
        cust.setCustomerUid(rawCustomer.getString("uid"));
        cust.setUid(rawCustomer.getString("unique_id"));
        cust.setFirstName(rawCustomer.getString("firstname"));
        cust.setLastName(rawCustomer.getString("lastname"));
        cust.setMobile(rawCustomer.getString("username"));
        cust.setEmail(rawCustomer.getString("email"));
        cust.setCrm(rawCustomer.getString("crm"));
        booking.setCustomer(cust);

        return booking;
    }
}
