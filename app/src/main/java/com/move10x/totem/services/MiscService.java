package com.move10x.totem.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by Ravi on 10/22/2015.
 */
public class MiscService {

    private String logTag = "miscService";


    public String shortenAddress(String address) {
        String shortAdd = "";
        String addArray[] = address.split(",");
        if(addArray.length > 3) {
            for (int i = 0; i < addArray.length - 1; i++) {
                if (i == 0)
                    shortAdd = shortAdd + addArray[i].trim();
                else
                    shortAdd = shortAdd + ", " + addArray[i].trim();
            }
        }
        else {
            shortAdd = address;
        }

        return shortAdd;
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     *//*
    private boolean checkPlayServices(Activity activity, Context context) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(logTag, "This device is not supported.");
                //finish();
            }
            return false;
        }

        return true;
    }*/
}
