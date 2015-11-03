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
        booking.setBookedBy(jsonObject.getString("booking_by"));
        booking.setReference(jsonObject.getString("booking_ref"));
        booking.setSource(jsonObject.getString("booking_source"));
        booking.setStatus(jsonObject.getString("booking_status"));
        booking.setDate(jsonObject.getString("booking_date"));
        booking.setTime(jsonObject.getString("booking_time"));
        booking.setPickup(jsonObject.getString("pickup_address"));
        booking.setDrop(jsonObject.getString("dest_address"));
        booking.setVehicleCategory(jsonObject.getString("tempo_type"));
        booking.setTotalJourneyTime(jsonObject.getString("total_journey_time"));
        booking.setTotalWaitingTime(jsonObject.getString("total_waiting_time"));
        booking.setTotalDistance(jsonObject.getString("total_distance_km"));
        booking.setTotalFare(jsonObject.getString("total_fare"));
        booking.setDiscount(jsonObject.getString("total_discount"));
        booking.setDriverFareShare(jsonObject.getString("driver_fare_share"));
        booking.setPaidToDriver(jsonObject.getString("paid_to_driver"));
        booking.setPaidToCompany(jsonObject.getString("paid_to_company"));
        booking.setDriverRating(jsonObject.getString("driver_rating"));
        booking.setCustomerRating(jsonObject.getString("customer_rating"));
        booking.setBookingCreatedAt(jsonObject.getString("created_at"));

        //Set customer.
        Customer cust = new Customer();
        JSONObject rawCustomer = jsonObject.getJSONObject("customerDetails");
        cust.setId(rawCustomer.getString("uid"));
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
