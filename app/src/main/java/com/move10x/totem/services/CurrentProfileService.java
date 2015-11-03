package com.move10x.totem.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.move10x.totem.models.CurrentProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ravi on 10/9/2015.
 */
public class CurrentProfileService {

    private Context _context = null;

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
                    Log.d("login", cursor.getString(0) + "," + cursor.getString(1));
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
            Log.e("login", "Failed to Create Profile. " + ex.getMessage());
          //  throw ex;
        } finally {
            dbInstance.endTransaction();
        }
    }

    public void ClearProfile(){
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
}

