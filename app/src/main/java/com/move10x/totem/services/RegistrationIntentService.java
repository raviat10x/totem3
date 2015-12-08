//package com.move10x.totem.services;
//
//import android.app.IntentService;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.AsyncTask;
//import android.preference.PreferenceManager;
//import android.support.v4.content.LocalBroadcastManager;
//import android.util.Log;
//
//import com.google.android.gms.gcm.GcmPubSub;
//import com.google.android.gms.gcm.GoogleCloudMessaging;
//import com.google.android.gms.iid.InstanceID;
//import com.move10x.totem.R;
//import com.move10x.totem.models.DatabaseHandler;
//import com.move10x.totem.models.QuickstartPreferences;
//import com.move10x.totem.models.UserFunctions;
//
////import org.jetbrains.annotations.NotNull;
////import org.jetbrains.annotations.Nullable;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.HashMap;
//
//public class RegistrationIntentService extends IntentService {
//
//    private static final String TAG = "RegIntentService";
//    private static final String[] TOPICS = {"global"};
//
//    private static String KEY_SUCCESS = "success";
//    private String driverUid;
//    public RegistrationIntentService() {
//        super(TAG);
//    }
//
//    SharedPreferences sharedPreferences;
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        /**
//         * Hashmap to load data from the Sqlite database
//         **/
//        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
//        HashMap driver = new HashMap();
//        driver = db.getDriverDetails();
//
//
//
//        driverUid = String.valueOf(driver.get("uid"));
//
//        try {
//            // In the (unlikely) event that multiple refresh operations occur simultaneously,
//            // ensure that they are processed sequentially.
//            synchronized (TAG) {
//                // [START register_for_gcm]
//                // Initially this call goes out to the network to retrieve the token, subsequent calls
//                // are local.
//                // [START get_token]
//                InstanceID instanceID = InstanceID.getInstance(this);
//                String token = instanceID.getToken(getString(R.string.SENDER_ID),
//                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
//                // [END get_token]
//                Log.i(TAG, "GCM Registration Token: " + token);
//
//                // TODO: Implement this method to send any registration to your app's servers.
//
//                //if(!sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false)) {
//                    sendRegistrationToServer(token);
//                //}
//
//                // Subscribe to topic channels
//                //subscribeTopics(token);
//
//                // [END register_for_gcm]
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Failed to complete token refresh", e);
//            // If an exception happens while fetching the new token or updating our registration data
//            // on a third-party server, this ensures that we'll attempt the update at a later time.
//            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
//        }
//    }
//
//    /**
//     * Persist registration to third-party servers.
//     *
//     * Modify this method to associate the user's GCM registration token with any server-side account
//     * maintained by your application.
//     *
//     * @param token The new token.
//     */
//    private void sendRegistrationToServer(String token) {
//        // Add custom implementation, as needed.
//        new sendRegIdToServer().execute(token);
//    }
//
//    /**
//     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
//     *
//     * @param token GCM token
//     * @throws IOException if unable to reach the GCM PubSub service
//     */
//    // [START subscribe_topics]
//    private void subscribeTopics( String token) throws IOException {
//        for (String topic : TOPICS) {
//            GcmPubSub pubSub = GcmPubSub.getInstance(this);
//            pubSub.subscribe(token, "/topics/" + topic, null);
//        }
//    }
//    // [END subscribe_topics]
//
//    private class sendRegIdToServer extends AsyncTask<String, Void, JSONObject> {
//        private ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute(){
//            super.onPreExecute();
//        }
//
//
//        @Override
//        protected JSONObject doInBackground(String... args){
//            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
//            String token = args[0];
//            UserFunctions userFunction = new UserFunctions();
//            JSONObject json = userFunction.sendGcmRegId(token, driverUid);
//            //Api will be impleted here to update employee reg id
//
//            db.updateDriverGcmRegid(token);
//            return json;
//        }
//
//        @Override
//        protected void onPostExecute( JSONObject json){
//            try {
//                if(json != null) {
//                    //TODO introduce check for failure response and update activity screen accordingly.
//                    if (json.getString(KEY_SUCCESS) != null) {
//
//                        String res = json.getString(KEY_SUCCESS);
//
//                        if (Integer.parseInt(res) == 1) {
//                            Log.i(TAG, "Successfully updated Reg ID on server");
//
//                            // You should store a boolean that indicates whether the generated token has been
//                            // sent to your server. If the boolean is false, send the token to your server,
//                            // otherwise your server should have already received the token.
//                            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
//
//                            // Notify UI that registration has completed, so the progress indicator can be hidden.
//                            Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
//                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(registrationComplete);
//
//
//                        } else {
//                            Log.i(TAG, "Updating Reg ID on App server failed");
//                        }
//                    }
//                    else {
//                        Log.i(TAG, "Updating Reg ID on App server failed");
//                    }
//                }
//                else {
//                    Log.i(TAG, "Updating Reg ID on App server failed");
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//}