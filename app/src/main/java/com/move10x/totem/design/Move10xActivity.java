package com.move10x.totem.design;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

//import com.move10x.totem.receivers.NetworkChangeReceiver;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.move10x.totem.models.QuickstartPreferences;
import com.move10x.totem.services.MiscService;
import com.move10x.totem.services.RegistrationIntentService;

/**
 * Created by Ravi on 11/3/2015.
 */
public class Move10xActivity extends AppCompatActivity {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "Move10xActi ity";
    private BroadcastReceiver mGcmRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MiscService.currentActivity = this;

        setupGcmBroadcastReceiver();

        mGcmRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive( Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    //TODO code to show 'Accepting Bookings' on the bottom status bar
                    Log.i(TAG, "GCM Updated on server. Now accepting GCM Messages!");
                } else {
                    //TODO code to show 'Not Accepting Bookings' on the bottom status bar
                }
            }
        };

    }

    private void setupGcmBroadcastReceiver() {
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                //finish();
            }
            return false;
        }

        return true;
    }

}
