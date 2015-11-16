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
    public static Context context;
    public static Activity currentActivity;


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
}
