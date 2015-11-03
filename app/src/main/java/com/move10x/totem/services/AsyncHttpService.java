package com.move10x.totem.services;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Ravi on 10/17/2015.
 */
public class AsyncHttpService {
    private Context context;

    private static AsyncHttpClient client = new AsyncHttpClient();

   public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
       Log.d("httpService", "getRequest: " + url);
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.d("httpService", "postRequest: " + url);
        client.post(url, params, responseHandler);
    }
}

