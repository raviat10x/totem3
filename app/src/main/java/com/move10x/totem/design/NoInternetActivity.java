package com.move10x.totem.design;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.move10x.totem.R;
//import com.move10x.totem.receivers.NetworkChangeReceiver;

public class NoInternetActivity extends AppCompatActivity {

    private String logTag = "noInternetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        TextView btnOnCheckInternet = (TextView) findViewById(R.id.btnOnCheckInternet);
        btnOnCheckInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkInternetConnection();
            }
        });

    }

//    private void checkInternetConnection() {
//        NetworkChangeReceiver service = new NetworkChangeReceiver();
//        int status = service.getConnectivityStatusString(getApplicationContext());
//        if (status == service.NETWORK_STATUS_NOT_CONNECTED) {
//            //Get to NoInternet Activity.
//            Log.d(logTag, "No internet connection.");
//            Toast.makeText(getBaseContext(), "No internet connection.", Toast.LENGTH_LONG);
//        } else {
//            //Redirect to login activity.
//            Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            getApplicationContext().startActivity(intent1);
//        }
//    }

}
