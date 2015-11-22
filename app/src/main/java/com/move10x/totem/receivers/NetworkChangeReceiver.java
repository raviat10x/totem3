package com.move10x.totem.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.move10x.totem.design.LoginActivity;
import com.move10x.totem.design.NoInternetActivity;

/**
 * Created by Ravi on 11/3/2015.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    private String logTag = "networkChangeReceiver";

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    public static final int NETWORK_STATUS_NOT_CONNECTED = 0, NETWORK_STAUS_WIFI = 1, NETWORK_STATUS_MOBILE = 2;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = getConnectivityStatusString(context);
        if (!"android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            if(status== NETWORK_STATUS_NOT_CONNECTED){
                //Get to NoInternet Activity.
                Log.d(logTag, "No internet connection.");
                //Redirect to no internet activity.
                Intent intent1 = new Intent(context, NoInternetActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);

            }else{
                //Redirect to login activity.
                Log.d(logTag, "Internet is on.");
                //Redirect to login button.
                Intent intent1 = new Intent(context, LoginActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        }
    }

    public int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public int getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        int status = 0;
        if (conn == TYPE_WIFI) {
            status = NETWORK_STAUS_WIFI;
        } else if (conn == TYPE_MOBILE) {
            status = NETWORK_STATUS_MOBILE;
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = NETWORK_STATUS_NOT_CONNECTED;
        }
        return status;
    }
}