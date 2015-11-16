package com.move10x.totem.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.move10x.totem.models.AppError;
import com.move10x.totem.models.CurrentProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ravi on 10/9/2015.
 */
public class CurrentProfileService {

    private Context _context = null;
    private String logTag = "currentProfileService";

    public CurrentProfileService(Context context) {
        this._context = context;
    }

    public void addAttribute(String key, String value) {

    }

    public void removeAttribute(String key) {

    }

    public boolean hasAttribute(String key) {
        return true;
    }

    public CurrentProfile getCurrentProfile() {
        Map<String, String> rawProfile = new HashMap<String, String>();
        SQLiteDatabase dbInstance = new SQLLiteService(_context).getDBInstance();
        Cursor cursor = dbInstance.rawQuery("SELECT * FROM TABLE_CurrentProfile;", null);
        int sum = 0;
        if (cursor.moveToFirst()) {
            try {
                do {
                    rawProfile.put(cursor.getString(0), cursor.getString(1));
                    Log.d(logTag, cursor.getString(0) + "," + cursor.getString(1));
                } while (cursor.moveToNext());

            } finally {
                cursor.close();
            }
        }

        CurrentProfile profile = new CurrentProfile(
                rawProfile.containsKey("userId") ? rawProfile.get("userId") : null,
                rawProfile.containsKey("firstName") ? rawProfile.get("firstName") : null,
                rawProfile.containsKey("lastName") ? rawProfile.get("lastName") : null,
                rawProfile.containsKey("userName") ? rawProfile.get("userName") : null,
                rawProfile.containsKey("phoneNumber") ? rawProfile.get("phoneNumber") : null,
                rawProfile.containsKey("userType") ? rawProfile.get("userType") : null,
                rawProfile.containsKey("imagePath") ? rawProfile.get("imagePath") : null);
        return profile;
    }

    public void CreateProfile(CurrentProfile profile) {
        Map<String, String> rawProfile = new HashMap<String, String>();
        SQLiteDatabase dbInstance = new SQLLiteService(_context).getDBInstance();

        dbInstance.beginTransaction();
        try {
            String query = "Delete from TABLE_CurrentProfile";
            dbInstance.execSQL(query);
            query = "Insert into TABLE_CurrentProfile (Key, Value) Values ('userId', '" + profile.getUserId() + "')";
            dbInstance.execSQL(query);
            query = "Insert into TABLE_CurrentProfile (Key, Value) Values ('firstName', '" + profile.getFirstName() + "')";
            dbInstance.execSQL(query);
            query = "Insert into TABLE_CurrentProfile (Key, Value) Values ('lastName', '" + profile.getLastName() + "')";
            dbInstance.execSQL(query);
            query = "Insert into TABLE_CurrentProfile (Key, Value) Values ('userName', '" + profile.getUserName() + "')";
            dbInstance.execSQL(query);
            query = "Insert into TABLE_CurrentProfile (Key, Value) Values ('phoneNumber', '" + profile.getPhoneNumber() + "')";
            dbInstance.execSQL(query);
            query = "Insert into TABLE_CurrentProfile (Key, Value) Values ('userType', '" + profile.getUserType() + "')";
            dbInstance.execSQL(query);
            query = "Insert into TABLE_CurrentProfile (Key, Value) Values ('imagePath', '" + profile.getImageUrl() + "')";
            dbInstance.execSQL(query);
            dbInstance.setTransactionSuccessful();
            Log.d("login", "Profile Created successfully.");
        } catch (Exception ex) {
            //Dont commit transaction
            Log.e(logTag, "Failed to Create Profile. " + ex.getMessage());
            //  throw ex;
        } finally {
            dbInstance.endTransaction();
        }
    }

    public void ClearProfile() {
        Map<String, String> rawProfile = new HashMap<String, String>();
        SQLiteDatabase dbInstance = new SQLLiteService(_context).getDBInstance();

        dbInstance.beginTransaction();
        try {
            String query = "Delete from TABLE_CurrentProfile";
            dbInstance.execSQL(query);
            dbInstance.setTransactionSuccessful();
        } catch (Exception ex) {
            //Dont commit transaction
            Log.d("currentProfileService", "Failed to Create Profile. " + ex.getMessage());
            //throw ex;
        } finally {
            dbInstance.endTransaction();
        }
    }

    public void logError(AppError error) {
        //Check if table is created.
        Map<String, String> rawProfile = new HashMap<String, String>();
        SQLiteDatabase dbInstance = new SQLLiteService(_context).getDBInstance();

        dbInstance.beginTransaction();
        try {

            Log.d(logTag, "Logging app error: " + error.toString());

            // CREATE TABLE AppError( message TEXT , activity TEXT, errorDetails TEXT, additionalInfo TEXT)";
            String query = "Insert into TABLE_AppError (message, activity, errorDetails, additionalInfo) Values ("
                    + "'" + error.getMessage() + "', "
                    + "'" + error.getActivity() + "', "
                    + "'" + error.getErrorDetails() + "', "
                    + "'" + error.getAdditionalInfo() + "'); ";
            Log.d(logTag, "Error Query: " + query);
            dbInstance.execSQL(query);
            dbInstance.setTransactionSuccessful();
            Log.d(logTag, "Error logged successfully.");
        } catch (Exception ex) {
            //Dont commit transaction
            Log.e(logTag, "Failed to log in sqllite. " + ex.getMessage());
        } finally {
            dbInstance.endTransaction();
        }
    }

    public ArrayList<AppError> getErrors() {
        ArrayList<AppError> errorList = new ArrayList<AppError>();

        SQLiteDatabase dbInstance = new SQLLiteService(_context).getDBInstance();
        Cursor cursor = dbInstance.rawQuery("select * from TABLE_AppError", null, null);
        // CREATE TABLE AppError( message TEXT , activity TEXT, errorDetails TEXT, additionalInfo TEXT)";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    AppError appError = new AppError();
                    appError.setMessage(cursor.getString(0));
                    appError.setErrorDetails(cursor.getString(1));
                    appError.setAdditionalInfo(cursor.getString(2));
                    appError.setActivity(cursor.getString(3));
                    errorList.add(appError);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return errorList;
    }
}

