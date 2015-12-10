package com.move10x.totem.design;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.move10x.totem.R;
import java.io.InputStream;
import java.net.URL;

public class NotificationActivity extends Activity {

    private static final String TAG ="NotificationActivity" ;
    private static String messageTitleData;
    private static String messageSubTitle;
    private static String msgContent;
    private Bitmap bitmap;

    private ImageView cancelImage;
    private static TextView messageTitle;
    private ImageView notificationImage;
    private static TextView messageContent;
    private static TextView messageCopyRight;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notificationscreen);

        cancelImage = (ImageView) findViewById(R.id.closeNotification);
        messageTitle = (TextView) findViewById(R.id.messageTitle);
        notificationImage = (ImageView) findViewById(R.id.imageNotification);
        messageContent = (TextView) findViewById(R.id.messageContent);
        messageCopyRight = (TextView) findViewById(R.id.messageCopyRight);
        getIntentExtras();
        setData();
        playSound();

        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
        {
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }


    
    private Handler windowCloseHandler = new Handler();
    
    private Runnable windowCloserRunnable = new Runnable() {
        @Override
        public void run() {
             ActivityManager am = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

            if (cn != null && cn.getClassName().equals("com.android.systemui.recent.RecentsActivity")) {
                toggleRecents();
            }
        }
    };


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!hasFocus) {
            windowCloseHandler.postDelayed(windowCloserRunnable, 250);
        }
    }

    private void toggleRecents() {
         Intent closeRecents = new Intent("com.android.systemui.recent.action.TOGGLE_RECENTS");
        closeRecents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
         ComponentName recents = new ComponentName("com.android.systemui", "com.android.systemui.recent.RecentsActivity");
        closeRecents.setComponent(recents);
        this.startActivity(closeRecents);
    }

    private void getIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            messageTitleData = extras.getString("messageTitle");
            messageSubTitle = extras.getString("messageSubTitle");
            msgContent = extras.getString("msgContent");
        }
    }



    private void setData()
    {
        if(messageTitleData != null)
        {
            messageTitle.setText(messageTitleData);
        }
        if(messageContent != null)
        {
            messageContent.setText(msgContent);
        }
        if(messageSubTitle != null)
        {
            messageCopyRight.setText(messageSubTitle);
        }
    }


    public void playSound()
    {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000});


         final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = notificationBuilder.build();
        notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notificationsound);
        //notification.flags |= Notification.FLAG_INSISTENT;
        notificationManager.notify(10, notification);

    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NotificationActivity.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();
        }

        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if (image != null) {
                notificationImage.setImageBitmap(image);
                pDialog.dismiss();

            } else {

                pDialog.dismiss();
                Toast.makeText(NotificationActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
