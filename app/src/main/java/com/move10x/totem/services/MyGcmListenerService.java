package com.move10x.totem.services;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.gcm.GcmListenerService;
import com.move10x.totem.design.NotificationActivity;
import com.move10x.totem.models.DatabaseHandler;
import com.move10x.totem.models.UserFunctions;
import org.json.JSONObject;
import java.util.HashMap;

public class MyGcmListenerService extends GcmListenerService {

    private CountDownTimer countDownTimer;

    private static final String TAG = "MyGcmListenerService";
    String booking_ref = "";
    String driverRegid = "";
    String driverUid = "";

    PowerManager pm;
    PowerManager.WakeLock wl;

    @Override
    public void onMessageReceived(String from, Bundle data) {

        long time = SystemClock.elapsedRealtime();
        String tag = data.getString("tag");
        Log.i(TAG, "GCM Message received with Tag: " + tag);

        pm = (PowerManager) getSystemService(POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);
        wl.acquire();

        if(tag.equals("notif"))
        {
            String messageTitle = data.getString("msgTitle");
            String messageSubTitle = data.getString("msgSubTitle");
            String msgContent = data.getString("msgContent");
            Intent intent = new Intent(this, NotificationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.putExtra("messageTitle", messageTitle);
            intent.putExtra("messageSubTitle", messageSubTitle);
            intent.putExtra("msgContent", msgContent);

            startActivity(intent);

            new acknowledgeGCM().execute();
        }
        else if(tag.equals("reset")) {

        }
    }

    private class acknowledgeGCM extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args){
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            HashMap driver = db.getDriverDetails();
            driverRegid = String.valueOf(driver.get("gcmRegid"));
            driverUid = String.valueOf(driver.get("uid"));
             UserFunctions userFunction = new UserFunctions();
             JSONObject json = userFunction.acknowledgeGCM(driverRegid, booking_ref, driverUid);
            return json;
        }

        @Override
        protected void onPostExecute( JSONObject json){
            super.onPostExecute(json);
            try {
                if(json != null) {
                    //TODO Success notice on GCM acknowledgement sent
                    Log.i(TAG, "GCM Message Acknowledged.");
                }
                else {
                    // TODO: Show error notice
                }
            } catch (Exception e) {
                e.printStackTrace();
                //TODO: Show error notice
            }
        }

    }
}