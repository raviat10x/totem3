//package com.move10x.totem.models;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
///**
// * Created by aakash on 20/05/15.
// */
//public class DatabaseHandler extends SQLiteOpenHelper {
//
//    // All Static variables
//    // Database Version
//    private static final int DATABASE_VERSION = 28;
//
//    // Database Name
//    private static final String DATABASE_NAME = "loggedin_driver";
//
//    // Login table name
//    private static final String TABLE_LOGIN = "login";
//    private static final String TABLE_BOOKINGS = "bookings";
//    private static final String TABLE_CURRENT_BOOKING_PERSIST = "current_booking";
//    private static final String TABLE_JOURNEY_VALUES = "journey_values";
//    private static final String TABLE_GLOBALS = "globals";
//    private static final String TABLE_DROPS = "drops";
//
//    // Login Table Columns names
//    private static final String KEY_ID = "id";
//    private static final String KEY_FIRSTNAME = "fname";
//    private static final String KEY_LASTNAME = "lname";
//    private static final String KEY_EMAIL = "email";
//    private static final String KEY_USERNAME = "uname";
//    private static final String KEY_UID = "uid";
//    private static final String KEY_JOINING_DATE = "joining_date";
//    private static final String KEY_TEMPO_MAKE = "tempo_make";
//    private static final String KEY_TEMPO_MODEL = "tempo_model";
//    private static final String KEY_TEMPO_CATEGORY = "tempo_category";
//    private static final String KEY_TEMPO_REGNO = "tempo_regno";
//    private static final String KEY_TEMPO_VIN = "tempo_vin";
//    private static final String KEY_DUTY_STATUS = "duty_status";
//    private static final String KEY_IMEI = "imei";
//    private static final String KEY_MSISDN = "msisdn";
//    private static final String KEY_ADDRESS = "address";
//    private static final String KEY_REGION = "region";
//    private static final String KEY_BASESTATION = "basestation";
//    private static final String KEY_APP_VERSION = "app_version";
//    private static final String KEY_PHONE_MODEL = "phone_model";
//    private static final String KEY_AVG_RATING = "avg_rating";
//    private static final String KEY_UNSETTLED_RUN = "unsettled_run";
//    private static final String KEY_UPDATED_AT = "updated_at";
//    private static final String KEY_AUTHORITY = "authority";
//    private static final String KEY_TOTAL_MONEY_EARNED = "totalEarned";
//    private static final String KEY_TOTAL_CHARGEABLE_DISTANCE = "totalChargeableDistance";
//    private static final String KEY_TOTAL_TRIPS = "totalTrips";
//    private static final String KEY_TODAY_EARNED = "todayEarned";
//    private static final String KEY_DRIVER_IMAGE_LINK = "driverImageLink";
//    private static final String KEY_WORK_STATUS = "work_status";
//    private static final String KEY_PLAN = "plan";
//    private static final String KEY_VEHICLE_BRANDED = "vehicleBranded";
//    private static final String KEY_CURRENT_BOOKING = "currentBooking";
//    private static final String KEY_JOURNEYID = "journeyId";
//    private static final String KEY_BOOKING_REF = "bookingRef";
//    private static final String KEY_START_TIME = "startTime";
//    private static final String KEY_CURRENT_JOURNEY_TIME_ELAPSED = "journeyTime";
//    private static final String KEY_CURRENT_WAITING_TIME_ELAPSED = "waitingTime";
//    private static final String KEY_DISTANCE_COVERED = "totalDistance";
//    private static final String KEY_CHARGEABLE_DISTANCE_COVERED = "chargeableDistance";
//    private static final String KEY_PICKED_UP_TIME = "pickedupTime";
//
//
//    private static final String KEY_BOOKINGID = "id";
//    private static final String KEY_BOOKINGREF = "ref";
//    private static final String KEY_BOOKINGSTATUS = "status";
//    private static final String KEY_BOOKINGTIME = "time";
//    private static final String KEY_BOOKINGDATE = "date";
//    private static final String KEY_BOOKINGPICKUP = "pickup";
//    private static final String KEY_BOOKINGDROP = "dropadd";
//    private static final String KEY_BOOKINGCOST = "cost";
//    private static final String KEY_BOOKINGTEMPO = "tempo";
//    private static final String KEY_BOOKINGDRIVERSHARE = "driverShare";
//    private static final String KEY_BOOKINGCHARGEABLEDISTANCE = "chargeableDistance";
//
//    private static final String KEY_CUSTNAME = "cust_name";
//    private static final String KEY_FAREEST = "fare_est";
//    private static final String KEY_PICKUPADD = "pickup_add";
//    private static final String KEY_DRIVERUID = "driver_uid";
//    private static final String KEY_CUSTPHONE = "cust_phone";
//    private static final String KEY_PICKUPLAT = "pickup_lat";
//    private static final String KEY_PICKUPLNG = "pickup_lng";
//    private static final String KEY_DESTADD = "dest_add";
//    private static final String KEY_DESTLAT = "dest_lat";
//    private static final String KEY_DESTLNG = "dest_lng";
//    private static final String KEY_RECIPIENTPHONE = "recipient_phone";
//    private static final String KEY_VEHICLEBASEFARE = "vehicle_base_fare";
//    private static final String KEY_FREERUN = "free_run";
//    private static final String KEY_DISTMULT = "dist_multiplier";
//    private static final String KEY_TIMEMULT = "time_multiplier";
//    private static final String KEY_WAITINGMULT = "waiting_multiplier";
//    private static final String KEY_FREEMINS = "free_mins";
//    private static final String KEY_VEHICLESURCHARGE = "vehicle_surcharge";
//    private static final String KEY_LABOURBASEFARE = "labour_base_fare";
//    private static final String KEY_LABOURFLOORMULT = "labour_floor_multiplier";
//    private static final String KEY_LABOURTIMEMULT = "labour_time_multiplier";
//    private static final String KEY_SURGEPERCENT = "surge_percent";
//    private static final String KEY_STAX = "stax";
//    private static final String KEY_PROMOCODE = "promo_code";
//    private static final String KEY_DISCOUNT_TYPE = "discount_type";
//    private static final String KEY_DISCOUNT_VAL = "discount_val";
//    private static final String KEY_DISCOUNT_MAX = "discount_max";
//    private static final String KEY_DISCOUNT_THRESHOLD = "discount_threshold";
//    private static final String KEY_APPLIED_TO = "applied_to";
//    private static final String KEY_GCM_REGID = "gcm_regid";
//    private static final String KEY_RECORDED_DROP_POINTS = "recordedDropPoints";
//    private static final String KEY_EXTRA_DROP_FARE = "extraDropFare";
//
//    private static final String KEY_START_LOAD_TIME = "start_load_time";
//    private static final String KEY_START_UNLOAD_TIME = "start_unload_time";
//    private static final String KEY_CURRENT_LOADING_TIME_ELAPSED = "loading_time_elapsed";
//    private static final String KEY_CURRENT_UNLOADING_TIME_ELAPSED = "unloading_time_elapsed";
//
//
//    private static final String TAG = "DatabaseHandler";
//
//    private static final String KEY_GLOBALSID = "globals_id";
//    private static final String KEY_UPGRADE_AVAILABLE = "upgrade_available";
//    private static final String KEY_UPGRADE_VERSION_NAME = "upgrade_version_name";
//    private static final String KEY_UPGRADE_VERSION_CODE = "upgrade_version_code";
//    private static final String KEY_PERCENT_SHARE = "percent_share";
//
//
//    private static final String KEY_DROP_ID = "drop_id";
//    private static final String KEY_TOTAL_DROPS = "totalDrops";
//    private static final String KEY_DROP_KEY = "drop_key";
//    private static final String KEY_DROP_ADDRESS = "dropAddress";
//    private static final String KEY_DROP_LAT = "dropLat";
//    private static final String KEY_DROP_LNG = "dropLng";
//
//
//    public DatabaseHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    // Creating Tables
//    @Override
//    public void onCreate(@NotNull SQLiteDatabase db) {
//        @NotNull String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
//                + KEY_ID + " INTEGER PRIMARY KEY,"
//                + KEY_FIRSTNAME + " TEXT,"
//                + KEY_LASTNAME + " TEXT,"
//                + KEY_EMAIL + " TEXT UNIQUE,"
//                + KEY_USERNAME + " TEXT,"
//                + KEY_UID + " TEXT,"
//                + KEY_TEMPO_MAKE + " TEXT,"
//                + KEY_TEMPO_MODEL + " TEXT,"
//                + KEY_TEMPO_CATEGORY + " TEXT,"
//                + KEY_TEMPO_REGNO + " TEXT,"
//                + KEY_TEMPO_VIN + " TEXT,"
//                + KEY_DUTY_STATUS + " TEXT,"
//                + KEY_IMEI + " TEXT,"
//                + KEY_MSISDN + " TEXT,"
//                + KEY_ADDRESS + " TEXT,"
//                + KEY_REGION + " TEXT,"
//                + KEY_BASESTATION + " TEXT,"
//                + KEY_APP_VERSION + " TEXT,"
//                + KEY_PHONE_MODEL + " TEXT,"
//                + KEY_AVG_RATING + " TEXT,"
//                + KEY_UNSETTLED_RUN + " TEXT,"
//                + KEY_UPDATED_AT + " TEXT,"
//                + KEY_AUTHORITY + " TEXT,"
//                + KEY_JOINING_DATE + " TEXT,"
//                + KEY_TOTAL_MONEY_EARNED + " TEXT,"
//                + KEY_TOTAL_CHARGEABLE_DISTANCE + " TEXT,"
//                + KEY_TOTAL_TRIPS + " TEXT,"
//                + KEY_DRIVER_IMAGE_LINK + " TEXT,"
//                + KEY_WORK_STATUS + " TEXT,"
//                + KEY_PLAN + " TEXT,"
//                + KEY_VEHICLE_BRANDED + " TEXT,"
//                + KEY_CURRENT_BOOKING + " TEXT,"
//                + KEY_TODAY_EARNED + " TEXT,"
//                + KEY_GCM_REGID + " TEXT" + ")";
//        db.execSQL(CREATE_LOGIN_TABLE);
//
//        @NotNull String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + "("
//                + KEY_BOOKINGID + " INTEGER PRIMARY KEY,"
//                + KEY_BOOKINGREF + " TEXT UNIQUE,"
//                + KEY_BOOKINGSTATUS + " TEXT,"
//                + KEY_BOOKINGDATE + " TEXT,"
//                + KEY_BOOKINGTIME + " TEXT,"
//                + KEY_BOOKINGPICKUP + " TEXT,"
//                + KEY_BOOKINGDROP + " TEXT,"
//                + KEY_BOOKINGCOST + " TEXT,"
//                + KEY_BOOKINGDRIVERSHARE + " TEXT,"
//                + KEY_BOOKINGTEMPO + " TEXT,"
//                + KEY_BOOKINGCHARGEABLEDISTANCE + " TEXT" + ")";
//        db.execSQL(CREATE_BOOKINGS_TABLE);
//
//        @NotNull String CREATE_JOURNEY_VALUES_TABLE = "CREATE TABLE " + TABLE_JOURNEY_VALUES + "("
//                + KEY_JOURNEYID + " INTEGER PRIMARY KEY,"
//                + KEY_BOOKING_REF + " TEXT UNIQUE,"
//                + KEY_START_TIME + " TEXT,"
//                + KEY_START_LOAD_TIME + " TEXT,"
//                + KEY_START_UNLOAD_TIME + " TEXT,"
//                + KEY_CURRENT_JOURNEY_TIME_ELAPSED + " TEXT,"
//                + KEY_CURRENT_WAITING_TIME_ELAPSED + " TEXT,"
//                + KEY_CURRENT_LOADING_TIME_ELAPSED + " TEXT,"
//                + KEY_CURRENT_UNLOADING_TIME_ELAPSED + " TEXT,"
//                + KEY_DISTANCE_COVERED + " TEXT,"
//                + KEY_CHARGEABLE_DISTANCE_COVERED + " TEXT,"
//                + KEY_RECORDED_DROP_POINTS + " TEXT,"
//                + KEY_PICKED_UP_TIME + " TEXT" + ")";
//        db.execSQL(CREATE_JOURNEY_VALUES_TABLE);
//
//        @NotNull String CREATE_CURRENT_BOOKING_PERSIST_TABLE = "CREATE TABLE " + TABLE_CURRENT_BOOKING_PERSIST + "("
//                + KEY_BOOKINGID + " INTEGER PRIMARY KEY,"
//                + KEY_BOOKINGREF + " TEXT UNIQUE,"
//                + KEY_CUSTNAME + " TEXT,"
//                + KEY_FAREEST + " TEXT,"
//                + KEY_PICKUPADD + " TEXT,"
//                + KEY_DRIVERUID + " TEXT,"
//                + KEY_CUSTPHONE + " TEXT,"
//                + KEY_PICKUPLAT + " TEXT,"
//                + KEY_PICKUPLNG + " TEXT,"
//                + KEY_DESTADD + " TEXT,"
//                + KEY_DESTLAT + " TEXT,"
//                + KEY_DESTLNG + " TEXT,"
//                + KEY_RECIPIENTPHONE + " TEXT,"
//                + KEY_VEHICLEBASEFARE + " TEXT,"
//                + KEY_FREERUN + " TEXT,"
//                + KEY_DISTMULT + " TEXT,"
//                + KEY_TIMEMULT + " TEXT,"
//                + KEY_WAITINGMULT + " TEXT,"
//                + KEY_FREEMINS + " TEXT,"
//                + KEY_VEHICLESURCHARGE + " TEXT,"
//                + KEY_LABOURBASEFARE + " TEXT,"
//                + KEY_LABOURFLOORMULT + " TEXT,"
//                + KEY_LABOURTIMEMULT + " TEXT,"
//                + KEY_SURGEPERCENT + " TEXT,"
//                + KEY_STAX + " TEXT,"
//                + KEY_DISCOUNT_TYPE + " TEXT,"
//                + KEY_DISCOUNT_VAL + " TEXT,"
//                + KEY_DISCOUNT_MAX + " TEXT,"
//                + KEY_DISCOUNT_THRESHOLD + " TEXT,"
//                + KEY_APPLIED_TO + " TEXT,"
//                + KEY_TOTAL_DROPS + " TEXT,"
//                + KEY_EXTRA_DROP_FARE + " TEXT,"
//                + KEY_PROMOCODE + " TEXT" + ")";
//        db.execSQL(CREATE_CURRENT_BOOKING_PERSIST_TABLE);
//
//        @NotNull String CREATE_GLOBALS = "CREATE TABLE " + TABLE_GLOBALS + "("
//                + KEY_GLOBALSID + " INTEGER PRIMARY KEY,"
//                + KEY_UPGRADE_AVAILABLE + " TEXT,"
//                + KEY_UPGRADE_VERSION_NAME + " TEXT,"
//                + KEY_UPGRADE_VERSION_CODE + " TEXT,"
//                + KEY_PERCENT_SHARE + " TEXT" + ")";
//        db.execSQL(CREATE_GLOBALS);
//
//        @NotNull String CREATE_DROPS = "CREATE TABLE " + TABLE_DROPS + "("
//                + KEY_DROP_ID + " INTEGER PRIMARY KEY,"
//                + KEY_DROP_KEY + " TEXT,"
//                + KEY_DROP_ADDRESS + " TEXT,"
//                + KEY_DROP_LAT + " TEXT,"
//                + KEY_DROP_LNG + " TEXT" + ")";
//        db.execSQL(CREATE_DROPS);
//    }
//
//    // Upgrading database
//    @Override
//    public void onUpgrade(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT_BOOKING_PERSIST);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNEY_VALUES);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GLOBALS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DROPS);
//        // Create tables again
//        onCreate(db);
//    }
//
//    /**
//     * Storing user details in database
//     * */
//    public void addDriver(String fname, String lname, String email, String uname, String uid, String tmake, String tmodel, String tcat, String treg, String tvin, String dutyStatus, String IMEI, String MSISDN, String address, String region, String basestation, String appVersion, String pmodel, String rating, String unsettledRun, String updatedAt, String authority, String joinDate, String driverImageLink, String workStatus, String plan, String vehicleBranded, String currentBooking) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_FIRSTNAME, fname); // FirstName
//        values.put(KEY_LASTNAME, lname); // LastName
//        values.put(KEY_EMAIL, email); // Email
//        values.put(KEY_USERNAME, uname); // UserName
//        values.put(KEY_UID, uid); // Driver UID
//        values.put(KEY_TEMPO_MAKE, tmake); //
//        values.put(KEY_TEMPO_MODEL, tmodel); //
//        values.put(KEY_TEMPO_CATEGORY, tcat); //
//        values.put(KEY_TEMPO_REGNO, treg); //
//        values.put(KEY_TEMPO_VIN, tvin); //
//        values.put(KEY_DUTY_STATUS, dutyStatus); //
//        values.put(KEY_IMEI, IMEI); //
//        values.put(KEY_MSISDN, MSISDN); //
//        values.put(KEY_ADDRESS, address); //
//        values.put(KEY_REGION, region); //
//        values.put(KEY_BASESTATION, basestation); //
//        values.put(KEY_APP_VERSION, appVersion); //
//        values.put(KEY_PHONE_MODEL, pmodel); //
//        values.put(KEY_AVG_RATING, rating); //
//        values.put(KEY_UNSETTLED_RUN, unsettledRun); //
//        values.put(KEY_UPDATED_AT, updatedAt); //
//        values.put(KEY_AUTHORITY, authority); //
//        values.put(KEY_JOINING_DATE, joinDate); // Created At
//        values.put(KEY_DRIVER_IMAGE_LINK, driverImageLink); //
//        values.put(KEY_WORK_STATUS, workStatus); //
//        values.put(KEY_PLAN, plan); //
//        values.put(KEY_VEHICLE_BRANDED, vehicleBranded); //
//        values.put(KEY_CURRENT_BOOKING, currentBooking); //
//        // Inserting Row
//        db.insert(TABLE_LOGIN, null, values);
//        db.close(); // Closing database connection
//    }
//
//    /**
//     * Storing Past Booking details in database
//     * */
//    public void addBookings(String b_ref, String b_status, String b_date, String b_time, String b_pickup, String b_drop, String b_cost, String b_tempo, String b_driverShare, String b_chargeableDistance) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_BOOKINGREF, b_ref); // Ref ID
//        values.put(KEY_BOOKINGSTATUS, b_status); // Status
//        values.put(KEY_BOOKINGDATE, b_date); // Booking Date
//        values.put(KEY_BOOKINGTIME, b_time); // Booking Time
//        values.put(KEY_BOOKINGPICKUP, b_pickup); // Pickup Address
//        values.put(KEY_BOOKINGDROP, b_drop); // Drop Address
//        values.put(KEY_BOOKINGCOST, b_cost); // Booking Cost
//        values.put(KEY_BOOKINGDRIVERSHARE, b_driverShare); // Money earned by driver
//        values.put(KEY_BOOKINGTEMPO, b_tempo); // Type of Tempo
//        values.put(KEY_BOOKINGCHARGEABLEDISTANCE, b_chargeableDistance); // Type of Tempo
//        // Inserting Row
//        db.insert(TABLE_BOOKINGS, null, values);
//        db.close(); // Closing database connection
//
//    }
//
//    /**
//     * Store values in globals database
//     * */
//    public void add_globals(String upgrade_available, String percent_share, String upgradeVersionName, String upgradeVersionCode) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_UPGRADE_AVAILABLE, upgrade_available); // Upgrade available
//        values.put(KEY_UPGRADE_VERSION_NAME, upgradeVersionName); // Upgrade available
//        values.put(KEY_UPGRADE_VERSION_CODE, upgradeVersionCode); // Upgrade available
//        values.put(KEY_PERCENT_SHARE, percent_share); // Percent Share
//
//        // Inserting Row
//        db.insert(TABLE_GLOBALS, null, values);
//        db.close(); // Closing database connection
//
//    }
//
//    /**
//     * Store values in Drop Points database
//     * */
//    public void addDropPoint(String dropId, String dropAddress, String dropLat, String dropLng) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_DROP_KEY, dropId); // Drop ID
//        values.put(KEY_DROP_ADDRESS, dropAddress); // Drop Address
//        values.put(KEY_DROP_LAT, dropLat); // Drop Latitude
//        values.put(KEY_DROP_LNG, dropLng); // Drop Longitude
//
//        // Inserting Row
//        db.insert(TABLE_DROPS, null, values);
//        db.close(); // Closing database connection
//
//    }
//
//    /**
//     * Store current journey values in database
//     * */
//    public void addJourneyValues(String b_ref, String start_time, String start_loading_time, String start_unloading_time, String journey_time, String waiting_time, String loading_time, String unloading_time, String dist_covered, String chargeable_dist_covered, String pickedupTime, String recordedDropPoints) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_BOOKING_REF, b_ref); // Ref ID
//        values.put(KEY_START_TIME, start_time); // Start time of booking
//        values.put(KEY_START_LOAD_TIME, start_loading_time); // Start time of booking
//        values.put(KEY_START_UNLOAD_TIME, start_unloading_time); // Start time of booking
//        values.put(KEY_CURRENT_JOURNEY_TIME_ELAPSED, journey_time); // Total journey time elapsed till now
//        values.put(KEY_CURRENT_WAITING_TIME_ELAPSED, waiting_time); // Total waiting time elapsed till now
//        values.put(KEY_CURRENT_LOADING_TIME_ELAPSED, loading_time); // Start time of booking
//        values.put(KEY_CURRENT_UNLOADING_TIME_ELAPSED, unloading_time); // Start time of booking
//        values.put(KEY_DISTANCE_COVERED, dist_covered); // Total distance covered till now
//        values.put(KEY_CHARGEABLE_DISTANCE_COVERED, chargeable_dist_covered); // Total chargeable distance covered till now
//        values.put(KEY_PICKED_UP_TIME, pickedupTime); // Time when customer was picked up.
//        values.put(KEY_RECORDED_DROP_POINTS, recordedDropPoints); // Time when customer was picked up.
//        // Inserting Row
//        db.insert(TABLE_JOURNEY_VALUES, null, values);
//        db.close(); // Closing database connection
//
//        Log.i(TAG, "Creating new row in Journey Values table for this booking.");
//    }
//
//    public void addCurrentBookingPersist(String b_ref, String cust_name, String fare_est, String pickup_add, String driver_uid, String cust_phone, String pickup_lat, String pickup_lng, String dest_add, String dest_lat, String dest_lng, String recipient_phone, String vehicle_base_fare, String free_run, String dist_mult, String time_mult, String waiting_mult, String free_mins, String vehicle_surcharge, String labour_base_fare, String labour_floor_mult, String labour_time_mult, String surge_percent, String stax, String promo_code, String discountType, String discountVal, String discountMax, String discountThreshold, String appliedTo, String totalDrops, String extraDropFare) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_BOOKINGREF, b_ref); // Ref ID
//        values.put(KEY_CUSTNAME, cust_name); // Status
//        values.put(KEY_FAREEST, fare_est); // Booking Date
//        values.put(KEY_PICKUPADD, pickup_add); // Booking Time
//        values.put(KEY_DRIVERUID, driver_uid); // Pickup Address
//        values.put(KEY_CUSTPHONE, cust_phone); // Drop Address
//        values.put(KEY_PICKUPLAT, pickup_lat); // Booking Cost
//        values.put(KEY_PICKUPLNG, pickup_lng); // Money earned by driver
//        values.put(KEY_DESTADD, dest_add); // Type of Tempo
//        values.put(KEY_DESTLAT, dest_lat); // Type of Tempo
//        values.put(KEY_DESTLNG, dest_lng); // Type of Tempo
//        values.put(KEY_RECIPIENTPHONE, recipient_phone); // Type of Tempo
//        values.put(KEY_VEHICLEBASEFARE, vehicle_base_fare); // Type of Tempo
//        values.put(KEY_FREERUN, free_run); // Type of Tempo
//        values.put(KEY_DISTMULT, dist_mult); // Type of Tempo
//        values.put(KEY_TIMEMULT, time_mult); // Type of Tempo
//        values.put(KEY_WAITINGMULT, waiting_mult); // Type of Tempo
//        values.put(KEY_FREEMINS, free_mins); // Type of Tempo
//        values.put(KEY_VEHICLESURCHARGE, vehicle_surcharge); // Type of Tempo
//        values.put(KEY_LABOURBASEFARE, labour_base_fare); // Type of Tempo
//        values.put(KEY_LABOURFLOORMULT, labour_floor_mult); // Type of Tempo
//        values.put(KEY_LABOURTIMEMULT, labour_time_mult); // Type of Tempo
//        values.put(KEY_SURGEPERCENT, surge_percent); // Type of Tempo
//        values.put(KEY_STAX, stax); // Type of Tempo
//        values.put(KEY_PROMOCODE, promo_code); // Type of Tempo
//        values.put(KEY_DISCOUNT_TYPE, discountType); //
//        values.put(KEY_DISCOUNT_VAL, discountVal); //
//        values.put(KEY_DISCOUNT_MAX, discountMax); //
//        values.put(KEY_DISCOUNT_THRESHOLD, discountThreshold); //
//        values.put(KEY_APPLIED_TO, appliedTo); //
//        values.put(KEY_TOTAL_DROPS, totalDrops); //
//        values.put(KEY_EXTRA_DROP_FARE, extraDropFare); //
//
//        // Inserting Row
//        db.insert(TABLE_CURRENT_BOOKING_PERSIST, null, values);
//        db.close(); // Closing database connection
//
//    }
//
//
//    /**
//     * Getting user data from database
//     * */
//    @NotNull
//    public HashMap<String, String> getDriverDetails(){
//        @NotNull HashMap<String,String> user = new HashMap<String,String>();
//        @NotNull String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // Move to first row
//        cursor.moveToFirst();
//        if(cursor.getCount() > 0){
//            user.put("fname", cursor.getString(1));
//            user.put("lname", cursor.getString(2));
//            user.put("email", cursor.getString(3));
//            user.put("uname", cursor.getString(4));
//            user.put("uid", cursor.getString(5));
//            user.put("tmake", cursor.getString(6));
//            user.put("tmodel", cursor.getString(7));
//            user.put("tcat", cursor.getString(8));
//            user.put("treg", cursor.getString(9));
//            user.put("tvin", cursor.getString(10));
//            user.put("dutyStatus", cursor.getString(11));
//            user.put("IMEI", cursor.getString(12));
//            user.put("MSISDN", cursor.getString(13));
//            user.put("address", cursor.getString(14));
//            user.put("region", cursor.getString(15));
//            user.put("baseStation", cursor.getString(16));
//            user.put("appVersion", cursor.getString(17));
//            user.put("pmodel", cursor.getString(18));
//            user.put("avgRating", cursor.getString(19));
//            user.put("unsettledRun", cursor.getString(20));
//            user.put("updatedAt", cursor.getString(21));
//            user.put("authority", cursor.getString(22));
//            user.put("joinDate", cursor.getString(23));
//            user.put("totalEarned", cursor.getString(24));
//            user.put("totalChargeableDistance", cursor.getString(25));
//            user.put("totalTrips", cursor.getString(26));
//            user.put("driverImageLink", cursor.getString(27));
//            user.put("workStatus", cursor.getString(28));
//            user.put("plan", cursor.getString(29));
//            user.put("vehicleBranded", cursor.getString(30));
//            user.put("currentBooking", cursor.getString(31));
//            user.put("todayEarned", cursor.getString(32));
//            user.put("gcmRegid", cursor.getString(33));
//
//        }
//        cursor.close();
//        db.close();
//        // return user
//        return user;
//    }
//
//    /**
//     * Get Past bookings data from database
//     * */
//    @NotNull
//    public ArrayList<Booking> getPastBookings(){
//        @NotNull ArrayList<Booking> bookingList = new ArrayList<Booking>();
//        @NotNull String selectQuery = "SELECT  * FROM " + TABLE_BOOKINGS;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if(cursor.moveToFirst()){
//            do{
//                @NotNull Booking booking = new Booking();
//                booking.setId(cursor.getString(0));
//                booking.setRef(cursor.getString(1));
//                booking.setStatus(cursor.getString(2));
//                booking.setDate(cursor.getString(3));
//                booking.setTime(cursor.getString(4));
//                booking.setPickup(cursor.getString(5));
//                booking.setDrop(cursor.getString(6));
//                booking.setCost(cursor.getString(7));
//                booking.setDriverShare(cursor.getString(8));
//                booking.setTempo(cursor.getString(9));
//                booking.setChargeableDistance(cursor.getString(10));
//                bookingList.add(booking);
//            }while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        // return list of Past Bookings
//        return bookingList;
//    }
//
//    /**
//     * Getting current booking data from database
//     * */
//    @NotNull
//    public HashMap<String, String> getJourneyValues(){
//        @NotNull HashMap<String,String> journeyValues = new HashMap<String,String>();
//        @NotNull String selectQuery = "SELECT * FROM " + TABLE_JOURNEY_VALUES;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // Move to first row
//        cursor.moveToFirst();
//       // Log.i(TAG, "Getting journey values from SQLITE ");
//        if(cursor.getCount() > 0){
//            journeyValues.put("booking_ref", cursor.getString(1));
//            journeyValues.put("startTime", cursor.getString(2));
//            journeyValues.put("startLoadingTime", cursor.getString(3));
//            journeyValues.put("startUnloadingTime", cursor.getString(4));
//            journeyValues.put("journeyTimeElapsed", cursor.getString(5));
//            journeyValues.put("waitingTimeElapsed", cursor.getString(6));
//            journeyValues.put("loadingTimeElapsed", cursor.getString(7));
//            journeyValues.put("unloadingTimeElapsed", cursor.getString(8));
//            journeyValues.put("distCovered", cursor.getString(9));
//            journeyValues.put("chargeableDistCovered", cursor.getString(10));
//            journeyValues.put("recordedDropPoints", cursor.getString(11));
//            journeyValues.put("pickedupTime", cursor.getString(12));
//
//            //Log.i(TAG, "Journey Values: " + journeyValues.toString());
//        }
//        cursor.close();
//        db.close();
//        return journeyValues;
//    }
//
//    /**
//     * Getting global values from database
//     * */
//    @NotNull
//    public HashMap<String, String> getGlobals(){
//        @NotNull HashMap<String,String> globals = new HashMap<String,String>();
//        @NotNull String selectQuery = "SELECT  * FROM " + TABLE_GLOBALS;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // Move to first row
//        cursor.moveToFirst();
//        Log.i(TAG, "Getting journey values from SQLITE ");
//        if(cursor.getCount() > 0){
//            globals.put("upgrade_available", cursor.getString(1));
//            globals.put("upgrade_version_name", cursor.getString(2));
//            globals.put("upgrade_version_code", cursor.getString(3));
//            globals.put("percent_share", cursor.getString(4));
//        }
//        cursor.close();
//        db.close();
//        return globals;
//    }
//
//    /**
//     * Getting Drop Point values from database
//     * */
//    @NotNull
//    public ArrayList<Drops> getDropPoints(){
//        @NotNull ArrayList<Drops> dropList = new ArrayList<Drops>();
//        @NotNull String selectQuery = "SELECT  * FROM " + TABLE_DROPS;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if(cursor.moveToFirst()){
//            do{
//                @NotNull Drops drop = new Drops();
//                drop.setId(cursor.getString(1));
//                drop.setAddress(cursor.getString(2));
//                drop.setLat(cursor.getString(3));
//                drop.setLng(cursor.getString(4));
//
//                dropList.add(drop);
//            }while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        // return list of All Drop Points
//        return dropList;
//    }
//
//
//    /**
//     * Getting current booking data from database
//     * */
//    @NotNull
//    public HashMap<String, String> getCurrentBookingPersist(){
//        @NotNull HashMap<String,String> currentBooking = new HashMap<String,String>();
//        @NotNull String selectQuery = "SELECT  * FROM " + TABLE_CURRENT_BOOKING_PERSIST;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // Move to first row
//        cursor.moveToFirst();
//        if(cursor.getCount() > 0){
//            currentBooking.put("booking_ref", cursor.getString(1));
//            currentBooking.put("custName", cursor.getString(2));
//            currentBooking.put("fareEst", cursor.getString(3));
//            currentBooking.put("pickupAdd", cursor.getString(4));
//            currentBooking.put("driverUid", cursor.getString(5));
//            currentBooking.put("custPhone", cursor.getString(6));
//            currentBooking.put("pickupLat", cursor.getString(7));
//            currentBooking.put("pickupLng", cursor.getString(8));
//            currentBooking.put("destAdd", cursor.getString(9));
//            currentBooking.put("destLat", cursor.getString(10));
//            currentBooking.put("destLng", cursor.getString(11));
//            currentBooking.put("recipientPhone", cursor.getString(12));
//            currentBooking.put("vehicleBaseFare", cursor.getString(13));
//            currentBooking.put("freeRun", cursor.getString(14));
//            currentBooking.put("distMultiplier", cursor.getString(15));
//            currentBooking.put("timeMultiplier", cursor.getString(16));
//            currentBooking.put("waitingMultiplier", cursor.getString(17));
//            currentBooking.put("freeMins", cursor.getString(18));
//            currentBooking.put("vehicleSurcharge", cursor.getString(19));
//            currentBooking.put("labourBaseFare", cursor.getString(20));
//            currentBooking.put("labourFloorMultiplier", cursor.getString(21));
//            currentBooking.put("labourTimeMultiplier", cursor.getString(22));
//            currentBooking.put("surgePercent", cursor.getString(23));
//            currentBooking.put("stax", cursor.getString(24));
//            currentBooking.put("discountType", cursor.getString(25));
//            currentBooking.put("discountVal", cursor.getString(26));
//            currentBooking.put("discountMax", cursor.getString(27));
//            currentBooking.put("discountThreshold", cursor.getString(28));
//            currentBooking.put("appliedTo", cursor.getString(29));
//            currentBooking.put("totalDrops", cursor.getString(30));
//            currentBooking.put("extraDropFare", cursor.getString(31));
//            currentBooking.put("promoCode", cursor.getString(32));
//        }
//        cursor.close();
//        db.close();
//        return currentBooking;
//    }
//
//    /**
//     * Getting current booking data from database
//     * */
//    @NotNull
//    public String getDriverStatus(){
//        @NotNull String driverStatus = "";
//        @NotNull String selectQuery = "SELECT  "+ KEY_DUTY_STATUS + " FROM " + TABLE_LOGIN;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // Move to first row
//        cursor.moveToFirst();
//        if(cursor.getCount() > 0){
//            driverStatus = cursor.getString(0);
//        }
//        cursor.close();
//        db.close();
//        return driverStatus;
//    }
//
//    public void updateDriverStatus(String status, String imei){
//        Log.i(TAG, "Updating driver status to : " + status);
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_DUTY_STATUS, status);
//        db.update(TABLE_LOGIN, values, KEY_IMEI + " = ?", new String[]{imei});
//        db.close();
//    }
//
//    public void updateRecordedDropPoints(String recordedDropPoints){
//        Log.i(TAG, "Updating recorded drop points in local DB to : " + recordedDropPoints);
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_RECORDED_DROP_POINTS, recordedDropPoints);
//        db.update(TABLE_JOURNEY_VALUES, values, null, null);
//        db.close();
//    }
//
//    public void updateDriverFinance(double totalMoneyEarned, double totalChargeableDistance, int totalTrips){
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_TOTAL_MONEY_EARNED, totalMoneyEarned);
//        values.put(KEY_TOTAL_CHARGEABLE_DISTANCE, totalChargeableDistance);
//        values.put(KEY_TOTAL_TRIPS, totalTrips);
//        db.update(TABLE_LOGIN, values,null, null);
//        db.close();
//    }
//
//    public void updateDriverGcmRegid(String gcmRegid){
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_GCM_REGID, gcmRegid);
//        db.update(TABLE_LOGIN, values,null, null);
//        db.close();
//    }
//
//    public void updateJourneyValuesFromDistanceBroadcast(String journeyTimeElapsed, String waitingTimeElapsed, String loadingTimeELapsed, String unloadingTimeElapsed){
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_CURRENT_JOURNEY_TIME_ELAPSED, journeyTimeElapsed);
//        values.put(KEY_CURRENT_WAITING_TIME_ELAPSED, waitingTimeElapsed);
//        values.put(KEY_CURRENT_LOADING_TIME_ELAPSED, loadingTimeELapsed);
//        values.put(KEY_CURRENT_UNLOADING_TIME_ELAPSED, unloadingTimeElapsed);
//        db.update(TABLE_JOURNEY_VALUES, values,null, null);
//        db.close();
//    }
//
//    public void updateJourneyValuesFromLocationUpdateService(String distCovered, String chargeableDistCovered){
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_DISTANCE_COVERED, distCovered);
//        values.put(KEY_CHARGEABLE_DISTANCE_COVERED, chargeableDistCovered);
//        db.update(TABLE_JOURNEY_VALUES, values,null, null);
//        db.close();
//    }
//
//    public void updateJourneyValuesStartLoadTime(String startLoadTime){
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_START_LOAD_TIME, startLoadTime);
//        db.update(TABLE_JOURNEY_VALUES, values,null, null);
//        db.close();
//    }
//
//    public void updateJourneyValuesStartTime(String startTime){
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_START_TIME, startTime);
//        db.update(TABLE_JOURNEY_VALUES, values,null, null);
//        db.close();
//    }
//
//    public void updateJourneyValuesStartUnloadTime(String startUnloadTime){
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_START_UNLOAD_TIME, startUnloadTime);
//        db.update(TABLE_JOURNEY_VALUES, values,null, null);
//        db.close();
//    }
//
//    public void updateCurrentBookingPersist(String destAddress, String destLat, String destLong, String recipientPhone){
//        SQLiteDatabase db = this.getWritableDatabase();
//        @NotNull ContentValues values = new ContentValues();
//        values.put(KEY_DESTADD, destAddress);
//        values.put(KEY_DESTLAT, destLat);
//        values.put(KEY_DESTLNG, destLong);
//        values.put(KEY_RECIPIENTPHONE, recipientPhone);
//        db.update(TABLE_CURRENT_BOOKING_PERSIST, values,null, null);
//        db.close();
//    }
//
//    /**
//     * Getting user login status
//     * return true if rows are there in table
//     * */
//    public int getRowCount() {
//        @NotNull String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        int rowCount = cursor.getCount();
//        db.close();
//        cursor.close();
//
//        // return row count
//        return rowCount;
//    }
//
//
//    /**
//     * Re crate database
//     * Delete all tables and create them again
//     * */
//    public void resetTables(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        // Delete All Rows
//        db.delete(TABLE_LOGIN, null, null);
//        db.delete(TABLE_BOOKINGS, null, null);
//        db.close();
//    }
//
//    /**
//     * Re create database
//     * Delete all tables and create them again
//     * */
//    public void resetBookingTable(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        // Delete All Rows
//        db.delete(TABLE_BOOKINGS, null, null);
//        db.close();
//    }
//
//    /**
//     * Re create database
//     * Delete all tables and create them again
//     * */
//    public void resetCurrentBookingPersist(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        // Delete All Rows
//        db.delete(TABLE_CURRENT_BOOKING_PERSIST, null, null);
//        db.close();
//    }
//
//    /**
//     *
//     * Delete journey value dtabase tables and create them again
//     * */
//    public void resetJourneyValues(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        // Delete All Rows
//        db.delete(TABLE_JOURNEY_VALUES, null, null);
//        db.close();
//    }
//
//    /**
//     *
//     * Delete journey value dtabase tables and create them again
//     * */
//    public void resetGlobals(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        // Delete All Rows
//        db.delete(TABLE_GLOBALS, null, null);
//        db.close();
//    }
//
//    /**
//     *
//     * Delete drop points DB table
//     * */
//    public void resetDropPoints(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        // Delete All Rows
//        db.delete(TABLE_DROPS, null, null);
//        db.close();
//    }
//
//}
