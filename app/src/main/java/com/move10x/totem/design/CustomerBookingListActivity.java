package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.Booking;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CustomerBookingListActivity extends Move10xActivity {


    private static final String TAG = "Customer Booking List";
    private String customerUId, logTag = "driverBookingListActivity";
    private List<Booking> allBookings, completedBookings, currentBookings, cancelledBookings, rejectedBookings;
    //ViewPager viewPager;
    //TabLayout tabLayout;
    FrameLayout bookingListFragementHolder;
    ProgressBar progressBar;
    LinearLayout bookingListContainer;
    private List<Booking> pastBookings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customer_bookings_all);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Customer Booking History");

        customerUId = getIntent().getStringExtra("customerUid");
        //viewPager = (ViewPager) findViewById(R.id.bookingListViewPager);
        //tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        bookingListFragementHolder = (FrameLayout) findViewById(R.id.bookingListFragementHolder);
        progressBar = (ProgressBar) findViewById(R.id.bookingListProgress);
        bookingListContainer = (LinearLayout) findViewById(R.id.bookingListContainer);

//        showProgress(false);
//        showProgress(true);

        //Fetch bookings.
        fetchBookings();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onmenuitem click. " + item.getItemId() + "," + R.id.home);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fetchBookings() {
        //Request Parameters.
        Log.d(TAG, "Fetch bookings for customer: " + customerUId);

        CustomerBookingsAllFragment cbf = new CustomerBookingsAllFragment();
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, cbf);
        ft.commit();


//        RequestParams bookingListParameters = new RequestParams();
//        bookingListParameters.put("customerUid", customerUId);
//        bookingListParameters.put("tag", "vrm_get_driver_bookings");
//
//        //Async Driverlist fetch.
//        AsyncHttpService.get(Url.apiBaseUrl, bookingListParameters, new JsonHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // If the response is JSONObject instead of expected JSONArray.
//                Log.d("driverFragment", "Parsing getDriverBookings() response: " + response);
//
//                try {
//
//                    if (response.getString("success") != null && response.getString("success").equals("1")) {
//
//                        //If response is success, build Models from Json.
//                        allBookings = new ArrayList<Booking>();
////                        completedBookings = new ArrayList<Booking>();
//                        currentBookings = new ArrayList<Booking>();
//                        cancelledBookings = new ArrayList<Booking>();
////                        rejectedBookings = new ArrayList<Booking>();
//                        pastBookings = new ArrayList<Booking>();
//
//                        JSONObject jsonNewBookingList = response.has("newBookings") ? response.getJSONObject("newBookings") : null;
//                        JSONObject jsonCompleteBookingList = response.has("completeBookings") ? response.getJSONObject("completeBookings") : null;
//                        JSONObject jsonRejectedBookingList = response.has("rejectedBookings") ? response.getJSONObject("rejectedBookings") : null;
//
//                        //Decode new bookings.
//                        Log.d(logTag, "Decoding new bookings. ");
//                        if (jsonNewBookingList != null) {
//                            Iterator<String> keys = jsonNewBookingList.keys();
//                            while (keys.hasNext()) {
//                                String key = keys.next();
//                                JSONObject jsonBooking = jsonNewBookingList.getJSONObject(key);
//                                Booking booking = Booking.decodeBookingListResponseJson(jsonBooking);
//
//                                allBookings.add(booking);
//                                currentBookings.add(booking);
//                            }
//                        }
//
//                        //Decode completed bookings.
//                        Log.d(logTag, "Decoding completed bookings. ");
//                        if (jsonCompleteBookingList != null) {
//                            Iterator<String> keys = jsonCompleteBookingList.keys();
//                            while (keys.hasNext()) {
//                                String key = keys.next();
//                                JSONObject jsonBooking = jsonCompleteBookingList.getJSONObject(key);
//                                Booking booking = Booking.decodeBookingListResponseJson(jsonBooking);
//
//                                allBookings.add(booking);
//                                completedBookings.add(booking);
//                            }
//                        }
//
//                        //Decode rejected bookings.
//                        Log.d(logTag, "Decoding rejected bookings. ");
//                        if (jsonRejectedBookingList != null) {
//                            Iterator<String> keys = jsonRejectedBookingList.keys();
//                            while (keys.hasNext()) {
//                                String key = keys.next();
//                                JSONObject jsonBooking = jsonRejectedBookingList.getJSONObject(key);
//                                Booking booking = Booking.decodeBookingListResponseJson(jsonBooking);
//
//                                allBookings.add(booking);
//                                rejectedBookings.add(booking);
//                            }
//                        }
//                        showProgress(false);
//
//                        //Setup TabLayout.
//                        setupTablayout();
//                    }
//                } catch (JSONException ex) {
//                    Log.e(logTag, "Failed to parse response. ");
//                    Log.e(logTag, Log.getStackTraceString(ex));
//                }
//            }
    }

//   public void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            bookingListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
//            bookingListContainer.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    bookingListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//            progressBar.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//            bookingListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }
}