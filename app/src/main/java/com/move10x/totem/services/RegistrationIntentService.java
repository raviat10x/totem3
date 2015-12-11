package com.move10x.totem.services;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.move10x.totem.R;
import com.move10x.totem.models.CurrentProfile;
import com.move10x.totem.models.DatabaseHandler;
import com.move10x.totem.models.Driver;
import com.move10x.totem.models.QuickstartPreferences;
import com.move10x.totem.models.UserFunctions;

//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    private static String KEY_SUCCESS = "success";
    private String crmUid;
    public RegistrationIntentService() {
        super(TAG);
    }

    SharedPreferences sharedPreferences;

    @Override
    protected void onHandleIntent(Intent intent) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        CurrentProfile cp = (new CurrentProfileService(getApplicationContext())).getCurrentProfile();
        cp.getUserId();
        crmUid = cp.getUserId();

        Log.d(TAG, "Current Driver Uid is : " + crmUid);

        try {

            synchronized (TAG) {

                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.SENDER_ID),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                Log.i(TAG, "GCM Registration Token: " + token);
                 sendRegistrationToServer(token);

            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        new sendRegIdToServer().execute(token);
    }

    private void subscribeTopics( String token) throws IOException {
        for (String topic : TOPICS) {
            GcmPubSub pubSub = GcmPubSub.getInstance(this);
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }

    private class sendRegIdToServer extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        //Api will be impleted here to update employee reg id

        @Override
        protected JSONObject doInBackground(String... args){
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            String token = args[0];
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.sendGcmRegId(token, crmUid);
            Log.d(TAG, "Show json Object" +json);

            db.updateCrmGcmRegid(token);
            return json;
        }

        @Override
        protected void onPostExecute( JSONObject json){
            try {
                if(json != null) {
                    if (json.getString(KEY_SUCCESS) != null) {

                        String res = json.getString(KEY_SUCCESS);

                        if (Integer.parseInt(res) == 1) {
                            Log.i(TAG, "Successfully updated Reg ID on server");
                            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
                            Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(registrationComplete);
                        } else {
                            Log.i(TAG, "Updating Reg ID on App server failed");
                        }
                    }
                    else {
                        Log.i(TAG, "Updating Reg ID on App server failed");
                    }
                }
                else {
                    Log.i(TAG, "Updating Reg ID on App server failed");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}