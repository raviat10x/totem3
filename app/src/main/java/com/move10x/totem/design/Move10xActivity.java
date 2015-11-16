package com.move10x.totem.design;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.move10x.totem.receivers.NetworkChangeReceiver;
import com.move10x.totem.services.MiscService;

/**
 * Created by Ravi on 11/3/2015.
 */
public class Move10xActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MiscService.currentActivity = this;
    }
}
