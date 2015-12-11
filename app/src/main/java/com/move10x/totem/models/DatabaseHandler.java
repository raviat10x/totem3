package com.move10x.totem.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aakash on 20/05/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 28;

    // Database Name
    private static final String DATABASE_NAME = "loggedin_driver";

    // Login table name
    private static final String TABLE_LOGIN = "login";
    private static final String TABLE_GLOBALS = "globals";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "fname";
    private static final String KEY_LASTNAME = "lname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "uname";
    private static final String KEY_UID = "uid";
    private static final String KEY_JOINING_DATE = "joining_date";
    private static final String KEY_TEMPO_MAKE = "tempo_make";
    private static final String KEY_TEMPO_MODEL = "tempo_model";
    private static final String KEY_TEMPO_CATEGORY = "tempo_category";
    private static final String KEY_TEMPO_REGNO = "tempo_regno";
    private static final String KEY_TEMPO_VIN = "tempo_vin";
    private static final String KEY_DUTY_STATUS = "duty_status";
    private static final String KEY_IMEI = "imei";
    private static final String KEY_MSISDN = "msisdn";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_REGION = "region";
    private static final String KEY_BASESTATION = "basestation";
    private static final String KEY_APP_VERSION = "app_version";
    private static final String KEY_PHONE_MODEL = "phone_model";
    private static final String KEY_AVG_RATING = "avg_rating";
    private static final String KEY_UNSETTLED_RUN = "unsettled_run";
    private static final String KEY_UPDATED_AT = "updated_at";
    private static final String KEY_AUTHORITY = "authority";
    private static final String KEY_TOTAL_MONEY_EARNED = "totalEarned";
    private static final String KEY_TOTAL_CHARGEABLE_DISTANCE = "totalChargeableDistance";
    private static final String KEY_TOTAL_TRIPS = "totalTrips";
    private static final String KEY_TODAY_EARNED = "todayEarned";
    private static final String KEY_DRIVER_IMAGE_LINK = "driverImageLink";
    private static final String KEY_WORK_STATUS = "work_status";
    private static final String KEY_PLAN = "plan";
    private static final String KEY_VEHICLE_BRANDED = "vehicleBranded";
    private static final String KEY_CURRENT_BOOKING = "currentBooking";
    private static final String KEY_GCM_REGID = "gcm_regid";

    private static final String TAG = "DatabaseHandler";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate( SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FIRSTNAME + " TEXT,"
                + KEY_LASTNAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_USERNAME + " TEXT,"
                + KEY_UID + " TEXT,"
                + KEY_TEMPO_MAKE + " TEXT,"
                + KEY_TEMPO_MODEL + " TEXT,"
                + KEY_TEMPO_CATEGORY + " TEXT,"
                + KEY_TEMPO_REGNO + " TEXT,"
                + KEY_TEMPO_VIN + " TEXT,"
                + KEY_DUTY_STATUS + " TEXT,"
                + KEY_IMEI + " TEXT,"
                + KEY_MSISDN + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_REGION + " TEXT,"
                + KEY_BASESTATION + " TEXT,"
                + KEY_APP_VERSION + " TEXT,"
                + KEY_PHONE_MODEL + " TEXT,"
                + KEY_AVG_RATING + " TEXT,"
                + KEY_UNSETTLED_RUN + " TEXT,"
                + KEY_UPDATED_AT + " TEXT,"
                + KEY_AUTHORITY + " TEXT,"
                + KEY_JOINING_DATE + " TEXT,"
                + KEY_TOTAL_MONEY_EARNED + " TEXT,"
                + KEY_TOTAL_CHARGEABLE_DISTANCE + " TEXT,"
                + KEY_TOTAL_TRIPS + " TEXT,"
                + KEY_DRIVER_IMAGE_LINK + " TEXT,"
                + KEY_WORK_STATUS + " TEXT,"
                + KEY_PLAN + " TEXT,"
                + KEY_VEHICLE_BRANDED + " TEXT,"
                + KEY_CURRENT_BOOKING + " TEXT,"
                + KEY_TODAY_EARNED + " TEXT,"
                + KEY_GCM_REGID + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        onCreate(db);
    }

    public void updateCrmGcmRegid(String gcmRegid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GCM_REGID, gcmRegid);
        Log.d(TAG, "Current gcmId is : " + gcmRegid);
        db.update(TABLE_LOGIN, values, null, null);
        db.close();
    }

    public HashMap<String, String> getDriverDetails(){
         HashMap<String,String> user = new HashMap<String,String>();
         String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("fname", cursor.getString(1));
            user.put("lname", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("uname", cursor.getString(4));
            user.put("uid", cursor.getString(5));
            user.put("tmake", cursor.getString(6));
            user.put("tmodel", cursor.getString(7));
            user.put("tcat", cursor.getString(8));
            user.put("treg", cursor.getString(9));
            user.put("tvin", cursor.getString(10));
            user.put("dutyStatus", cursor.getString(11));
            user.put("IMEI", cursor.getString(12));
            user.put("MSISDN", cursor.getString(13));
            user.put("address", cursor.getString(14));
            user.put("region", cursor.getString(15));
            user.put("baseStation", cursor.getString(16));
            user.put("appVersion", cursor.getString(17));
            user.put("pmodel", cursor.getString(18));
            user.put("avgRating", cursor.getString(19));
            user.put("unsettledRun", cursor.getString(20));
            user.put("updatedAt", cursor.getString(21));
            user.put("authority", cursor.getString(22));
            user.put("joinDate", cursor.getString(23));
            user.put("totalEarned", cursor.getString(24));
            user.put("totalChargeableDistance", cursor.getString(25));
            user.put("totalTrips", cursor.getString(26));
            user.put("driverImageLink", cursor.getString(27));
            user.put("workStatus", cursor.getString(28));
            user.put("plan", cursor.getString(29));
            user.put("vehicleBranded", cursor.getString(30));
            user.put("currentBooking", cursor.getString(31));
            user.put("todayEarned", cursor.getString(32));
            user.put("gcmRegid", cursor.getString(33));

        }
        cursor.close();
        db.close();
        // return user
        return user;
    }


    public String getDriverStatus(){
         String driverStatus = "";
         String selectQuery = "SELECT  "+ KEY_DUTY_STATUS + " FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            driverStatus = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return driverStatus;
    }

    public void updateDriverStatus(String status, String imei){
        Log.i(TAG, "Updating driver status to : " + status);
        SQLiteDatabase db = this.getWritableDatabase();
         ContentValues values = new ContentValues();
        values.put(KEY_DUTY_STATUS, status);
        db.update(TABLE_LOGIN, values, KEY_IMEI + " = ?", new String[]{imei});
        db.close();
    }

    public void resetGlobals(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_GLOBALS, null, null);
        db.close();
    }

}
