package com.move10x.totem.models;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.move10x.totem.design.LoginActivity;
import com.move10x.totem.design.MainActivity;
import com.move10x.totem.design.NoInternetActivity;
//import com.move10x.totem.receivers.NetworkChangeReceiver;
import com.move10x.totem.services.CurrentProfileService;
import com.move10x.totem.services.MiscService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Ravi on 11/4/2015.
 */
public class JsonHttpResponseHandler extends com.loopj.android.http.JsonHttpResponseHandler {

    private String logTag = "httpResponseHandler";

//    @Override
//    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//        super.onFailure(statusCode, headers, throwable, errorResponse);
//        //Check for internet.
//        NetworkChangeReceiver service = new NetworkChangeReceiver();
//        int status = service.getConnectivityStatusString(MiscService.context);
//        if (status == service.NETWORK_STATUS_NOT_CONNECTED) {
//            //Get to NoInternet Activity. Redirect to no internet activity.
//            Intent intent1 = new Intent(MiscService.context, NoInternetActivity.class);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            MiscService.context.startActivity(intent1);
//
//        }else{
//            //Internet connected, other issue.
//            logError(statusCode, headers, throwable, errorResponse == null? "" : errorResponse.toString());
//            redirectToLoginActivity();
//        }
//    }

//    @Override
//    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//        super.onFailure(statusCode, headers, throwable, errorResponse);
//
//        //Check for internet.
//        NetworkChangeReceiver service = new NetworkChangeReceiver();
//        int status = service.getConnectivityStatusString(MiscService.context);
//        if (status == service.NETWORK_STATUS_NOT_CONNECTED) {
//            //Get to NoInternet Activity. Redirect to no internet activity.
//            Intent intent1 = new Intent(MiscService.context, NoInternetActivity.class);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            MiscService.context.startActivity(intent1);
//
//        }else{
//            //Internet connected, other issue.
//            logError(statusCode, headers, throwable, errorResponse == null ? "" : errorResponse.toString());
//            redirectToLoginActivity();
//        }
//    }

//    @Override
//    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//        super.onFailure(statusCode, headers, responseString, throwable);
//
//        //Check for internet.
//        NetworkChangeReceiver service = new NetworkChangeReceiver();
//        int status = service.getConnectivityStatusString(MiscService.context);
//        if (status == service.NETWORK_STATUS_NOT_CONNECTED) {
//            //Get to NoInternet Activity. Redirect to no internet activity.
//            Intent intent1 = new Intent(MiscService.context, NoInternetActivity.class);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            MiscService.context.startActivity(intent1);
//
//        }else{
//            //Internet connected, other issue.
//            logError(statusCode, headers, throwable, responseString == null? "" : responseString.toString());
//            redirectToLoginActivity();
//        }
//    }

    public void logError(int statusCode, Header[] headers, Throwable error, String response) {
        Log.d(logTag, "On Http Call Failure");

        //Log error if its not internet connection issue.
        AppError appError = new AppError();
        appError.setMessage("Failed to call Url: " + getRequestURI().getPath() + getRequestURI() + ", Status Code: " + statusCode);

        //Log Error details
        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));
        appError.setErrorDetails(sw.toString());
        appError.setAdditionalInfo("HTTP Response:" + response + "\n Profile: " + (new CurrentProfileService(MiscService.context)).getCurrentProfile());
        appError.setAdditionalInfo(MiscService.currentActivity.getLocalClassName());
        new CurrentProfileService(MiscService.context).logError(appError);
    }

    private void redirectToLoginActivity(){
        Toast.makeText(MiscService.context, "Internal Error Occured.", Toast.LENGTH_LONG).show();
        //Close activity and return to previous page.
        Intent intent = new Intent(MiscService.context, LoginActivity.class);
        intent.putExtra("loadFragment", "drivers");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MiscService.context.startActivity(intent);
    }
}