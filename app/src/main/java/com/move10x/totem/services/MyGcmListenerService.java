//package com.move10x.totem.services;
//
///**
// * Created by aakash on 16/06/15.
// */
//import android.app.DownloadManager;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.ProgressDialog;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.PowerManager;
//import android.os.SystemClock;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.android.gms.gcm.GcmListenerService;
//import org.json.JSONObject;
//
//import java.lang.reflect.Array;
//import java.util.HashMap;
//
//public class MyGcmListenerService extends GcmListenerService {
//
//    private CountDownTimer countDownTimer;
//
//    private static final String TAG = "MyGcmListenerService";
//
//    private Handler cancelBookingBroadcastHandler = new Handler();
//    private Handler reassignBookingBroadcastHandler = new Handler();
//
//    private Handler appUpgradeBroadcastHandler = new Handler();
//    Intent bookingCancelBroadcastIntent;
//    Intent bookingReassignBroadcastIntent;
//    Intent appUpgradeBroadcastIntent;
//    String canceledBy;
//    private String newAppVersionName;
//    private int newAppVersionCode;
//    private String appUri;
//    private String changeLog;
//    private String criticality;
//    private int versionCode;
//
//    String booking_ref = "";
//    String driverRegid = "";
//    String driverUid = "";
//
//    PowerManager pm;
//    PowerManager.WakeLock wl;
//
//    /**
//     * Called when message is received.
//     *
//     * @param from SenderID of the sender.
//     * @param data Data bundle containing message data as key/value pairs.
//     *             For Set of keys use data.keySet().
//     */
//    // [START receive_message]
//    @Override
//    public void onMessageReceived(String from, Bundle data) {
//
//        long time = SystemClock.elapsedRealtime();
//        String tag = data.getString("tag");
//        Log.i(TAG, "GCM Message received with Tag: " + tag);
//
//        pm = (PowerManager) getSystemService(POWER_SERVICE);
//        wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);
//        wl.acquire();
//
//        if(tag.equals("notif"))
//        {
//            String messageTitle = data.getString("msgTitle");
//            String imageLink = data.getString("imageLink");
//
//            String bookingReferenceNumber = data.getString("booking_ref");
//            String messageSubTitle = data.getString("msgSubTitle");
//            String msgContent = data.getString("msgContent");
//            Intent intent = new Intent(this, NotificationActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            intent.putExtra("messageTitle", messageTitle);
//            intent.putExtra("imageLink",imageLink);
//            intent.putExtra("bookingReferenceNumber",bookingReferenceNumber);
//            intent.putExtra("messageSubTitle",messageSubTitle);
//            intent.putExtra("msgContent",msgContent);
//
//            startActivity(intent);
//
//            new acknowledgeGCM().execute();
//        }
//
//        else if(tag.equals("newBooking")) {
//
//            Log.i(TAG,"New Booking Received: " + data.toString());
//
//
//
//            cancelBookingBroadcastHandler.removeCallbacks(sendBookingCancelToActiveBooking);
//
//            booking_ref = data.getString("booking_ref");
//            String cust_name = data.getString("cust_name");
//            String pickupAdd = data.getString("pickupAdd");
//            String fareEst = data.getString("fareEst");
//            String cust_phone = data.getString("cust_phone");
//            String pickupLat = data.getString("pickupLat");
//            String pickupLong = data.getString("pickupLong");
//
//            String destAdd = data.getString("destAdd");
//            String destLat = data.getString("destLat");
//            String destLng = data.getString("destLng");
//            String recipientPhone = data.getString("recipientPhone");
//            String vehicle_base_fare = data.getString("vehicle_base_fare");
//            String free_run = data.getString("free_run");
//            String dist_multiplier = data.getString("dist_multiplier");
//            String time_multiplier = data.getString("time_multiplier");
//            String waiting_multiplier = data.getString("waiting_multiplier");
//            String free_mins = data.getString("free_mins");
//            String vehicle_surcharge = data.getString("vehicle_surcharge");
//            String labour_base_fare = data.getString("labour_base_fare");
//            String labour_floor_multiplier = data.getString("labour_floor_multiplier");
//            String labour_time_multiplier = data.getString("labour_time_multiplier");
//            String surge_percent = data.getString("surge_percent");
//            String stax = data.getString("stax");
//            String promoCode = data.getString("promoCode");
//            String isCancelable = data.getString("isCancelable");
//            String discountType = data.getString("discountType");
//            String discountVal = data.getString("discountVal");
//            String discountMax = data.getString("discountMax");
//            String discountThreshold = data.getString("discountThreshold");
//            String appliedTo = data.getString("appliedTo");
//            String wayTo = data.getString("wayTo");
//            String extraDropFare = data.getString("extraDropFare");
//
//            Log.i(TAG, "From: " + from);
//            Log.i(TAG, "Reference: " + booking_ref);
//            Log.i(TAG, "isCancelable: " + isCancelable);
//
//            /**
//             * Start IncomingRequestActivity and show a notification indicating to the driver
//             * that a booking was received.
//             */
//            Intent intent = new Intent(this, IncomingRequestActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            intent.putExtra("received_time", time);
//            intent.putExtra("booking_ref", booking_ref);
//            intent.putExtra("cust_name", cust_name);
//            intent.putExtra("pickupAdd", pickupAdd);
//            intent.putExtra("fareEst", fareEst);
//            intent.putExtra("cust_phone", cust_phone);
//            intent.putExtra("pickupLat", pickupLat);
//            intent.putExtra("pickupLong", pickupLong);
//            intent.putExtra("isCancelable", isCancelable);
//            intent.putExtra("destAdd", destAdd);
//            intent.putExtra("destLat", destLat);
//            intent.putExtra("destLng", destLng);
//            intent.putExtra("recipientPhone", recipientPhone);
//            intent.putExtra("vehicle_base_fare", vehicle_base_fare);
//            intent.putExtra("free_run", free_run);
//            intent.putExtra("dist_multiplier", dist_multiplier);
//            intent.putExtra("time_multiplier", time_multiplier);
//            intent.putExtra("waiting_multiplier", waiting_multiplier);
//            intent.putExtra("free_mins", free_mins);
//            intent.putExtra("vehicle_surcharge", vehicle_surcharge);
//            intent.putExtra("labour_base_fare", labour_base_fare);
//            intent.putExtra("labour_floor_multiplier", labour_floor_multiplier);
//            intent.putExtra("labour_time_multiplier", labour_time_multiplier);
//            intent.putExtra("surge_percent", surge_percent);
//            intent.putExtra("stax", stax);
//            intent.putExtra("promoCode", promoCode);
//            intent.putExtra("discountType", discountType);
//            intent.putExtra("discountVal", discountVal);
//            intent.putExtra("discountMax", discountMax);
//            intent.putExtra("discountThreshold", discountThreshold);
//            intent.putExtra("appliedTo", appliedTo);
//            intent.putExtra("wayTo", wayTo);
//            intent.putExtra("extraDropFare", extraDropFare);
//
//            startActivity(intent);
//            sendNotification(pickupAdd);
//
//            new acknowledgeGCM().execute();
//
//        }
//        else if(tag.equals("updateBooking")) {
//            @NotNull DatabaseHandler db = new DatabaseHandler(getApplicationContext());
//            db.updateCurrentBookingPersist(data.getString("dest_address"), data.getString("dest_lat"), data.getString("dest_long"), data.getString("recipient_phone"));
//            Log.i(TAG, "Updated database with new details received from GCM");
//
//            new acknowledgeGCM().execute();
//        }
//        else if(tag.equals("cancelBooking")) {
//            canceledBy = data.getString("canceledBy");
//
//            bookingCancelBroadcastIntent = new Intent(Constants.BROADCAST_BOOKING_CANCEL_ACTION);
//            cancelBookingBroadcastHandler.post(sendBookingCancelToActiveBooking);
//
//            new acknowledgeGCM().execute();
//        }
//
//        else if(tag.equals("reassignBooking")) {
//            canceledBy = data.getString("canceledBy");
//
//            bookingReassignBroadcastIntent = new Intent(Constants.BROADCAST_BOOKING_REASSIGN_ACTION);
//            reassignBookingBroadcastHandler.post(sendBookingReassignToActiveBooking);
//
//            new acknowledgeGCM().execute();
//        }
//
//        else if(tag.equals("upgrade")) {
//
//            newAppVersionName = data.getString("appVersionName");
//            newAppVersionCode = Integer.valueOf(data.getString("appVersionCode"));
//            appUri = data.getString("appUri");
//            changeLog = data.getString("changeLog");
//            criticality = data.getString("criticality");
//            appUpgradeBroadcastIntent = new Intent(Constants.BROADCAST_APP_UPGRADE_ACTION);
//
//            //Overall information about the contents of a package
//            //This corresponds to all of the information collected from AndroidManifest.xml.
//            PackageInfo pInfo = null;
//            try {
//                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//            }
//            catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//            //get the app version Name for display
//            String version = pInfo.versionName;
//            //get the app version Code for checking
//            versionCode = pInfo.versionCode;
//
//            if(newAppVersionCode > versionCode) {
//                Log.i(TAG, "Upgrade message received. Downloading new update!");
//                appUpgradeBroadcastHandler.post(downloadAppUpgrade);
//                //playSound();
//
//            }
//            else {
//                Log.i(TAG, "Upgrade message received. App already up to date!");
//            }
//
//            new acknowledgeGCM().execute();
//        }
//
//        else if(tag.equals("reset")) {
//
//            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
//            HashMap currentBooking = new HashMap();
//            currentBooking = db.getCurrentBookingPersist();
//            if(currentBooking != null) {
//                db.resetCurrentBookingPersist();
//                db.resetJourneyValues();
//            }
//
//            /**
//             * Start MainActivity
//             */
//            @NotNull Intent intent = new Intent(this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//
//            new acknowledgeGCM().execute();
//
//        }
//
//
//    }
//
//    public void playSound()
//    {
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000});
//
//
//        @NotNull final NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Notification notification = notificationBuilder.build();
//        notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.notificationsound);
//        //notification.flags |= Notification.FLAG_INSISTENT;
//        notificationManager.notify(10, notification);
//
//    }
//
//
//    // [END receive_message]
//
//    /**
//     * Send an internal broadcast to Active Booking Activity informing it about Canceled Booking
//     * Necessary steps are taken at Active Booking Activity
//     */
//    @NotNull
//    private Runnable sendBookingCancelToActiveBooking = new Runnable() {
//        @Override
//        public void run() {
//            bookingCancelBroadcastIntent.putExtra("canceledBy", canceledBy);
//            sendBroadcast(bookingCancelBroadcastIntent);
//            sendCancelNotification("Booking canceled by "+ canceledBy + ".");
//        }
//    };
//
//    /**
//     * Send an internal broadcast to Active Booking Activity informing it about Reassigned Booking
//     * Necessary steps are taken at Active Booking Activity
//     */
//    @NotNull
//    private Runnable sendBookingReassignToActiveBooking = new Runnable() {
//        @Override
//        public void run() {
//            bookingReassignBroadcastIntent.putExtra("canceledBy", canceledBy);
//            sendBroadcast(bookingReassignBroadcastIntent);
//            sendCancelNotification("Booking canceled by "+ canceledBy + ".");
//        }
//    };
//
//    private DownloadManager downloadManager;
//    private long downloadReference;
//    /**
//     * Send an internal broadcast to Main Activity informing it about an new version of app available
//     * Necessary steps are taken at Active Booking Activity
//     */
//    @NotNull
//    private Runnable downloadAppUpgrade = new Runnable() {
//        @Override
//        public void run() {
//            downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//            Uri Download_Uri = Uri.parse(appUri);
//            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
//            request.setAllowedOverRoaming(true);
//            request.setTitle("MOVE10X App Upgrade Download");
//            //request.setDestinationInExternalFilesDir(getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, "MOVE10XPartner-" + newAppVersionCode + ".apk");
//            //request.setVisibleInDownloadsUi(true);
//            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "MOVE10XPartner-" + newAppVersionCode + ".apk");
//            downloadReference = downloadManager.enqueue(request);
//
//            // Send broadcast intent to MainActivity to show notification for new upgrade available.
//            appUpgradeBroadcastIntent.putExtra("appVersionName", newAppVersionName);
//            appUpgradeBroadcastIntent.putExtra("appVersionCode", newAppVersionCode);
//            appUpgradeBroadcastIntent.putExtra("downloadReference", downloadReference);
//            appUpgradeBroadcastIntent.putExtra("changeLog", changeLog);
//            sendBroadcast(appUpgradeBroadcastIntent);
//        }
//    };
//
//
//    /**
//     * Create and show a simple notification showing new Booking request.
//     *
//     * @param message GCM message received.
//     */
//    private void sendNotification(String message) {
//        @NotNull Intent intent = new Intent(this, IncomingRequestActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_logo)
//                .setContentTitle("New Move Request")
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setOngoing(true)
//                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000})
//                .setContentIntent(pendingIntent);
//
//        @NotNull final NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Notification notification = notificationBuilder.build();
//        notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.newbookingalarm1);
//        notification.flags |= Notification.FLAG_INSISTENT;
//        notificationManager.notify(0 /* ID of notification */, notification);
//
//    }
//
//    /**
//     * Create and show a simple notification showing new Booking request.
//     *
//     * @param message GCM message received.
//     */
//    private void sendCancelNotification(String message) {
//        @NotNull Intent intent = new Intent(this, ActiveBookingActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_logo)
//                .setContentTitle("Booking Canceled")
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setOngoing(false)
//                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000});
//
//        @NotNull final NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Notification notification = notificationBuilder.build();
//        notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.bookingcancelalarm);
//        //notification.flags |= Notification.FLAG_INSISTENT;
//        notificationManager.notify(10, notification);
//
//    }
//
//
//
//    @Override
//    public void onDestroy() {
//        Log.i(TAG, "gcmListener destroyed");
//        if(wl!= null) {
//            wl.release();
//        }
//        super.onDestroy();
//    }
//
//    /**
//     * Async task to send Booking reassigned confirmation to server
//     */
//    private class acknowledgeGCM extends AsyncTask<String, Void, JSONObject> {
//        private ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute(){
//            super.onPreExecute();
//        }
//
//        @Nullable
//        @Override
//        protected JSONObject doInBackground(String... args){
//            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
//            HashMap driver = db.getDriverDetails();
//            driverRegid = String.valueOf(driver.get("gcmRegid"));
//            driverUid = String.valueOf(driver.get("uid"));
//            @NotNull UserFunctions userFunction = new UserFunctions();
//            @Nullable JSONObject json = userFunction.acknowledgeGCM(driverRegid, booking_ref, driverUid);
//            return json;
//        }
//
//        @Override
//        protected void onPostExecute(@Nullable JSONObject json){
//            super.onPostExecute(json);
//            try {
//                if(json != null) {
//                    //TODO Success notice on GCM acknowledgement sent
//                    Log.i(TAG, "GCM Message Acknowledged.");
//                }
//                else {
//                    // TODO: Show error notice
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                //TODO: Show error notice
//            }
//        }
//
//    }
//}