package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.Booking;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class DriverBookingList extends Move10xActivity {

    private String driverUId, logTag = "driverBookingListActivity";
    private List<Booking> allBookings, completedBookings, currentBookings, cancelledBookings, rejectedBookings;
    ViewPager viewPager;
    TabLayout tabLayout;
    ProgressBar progressBar;
    LinearLayout bookingListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_booking_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Booking History");

        driverUId = getIntent().getStringExtra("driverUid");
        viewPager = (ViewPager) findViewById(R.id.bookingListViewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        progressBar = (ProgressBar) findViewById(R.id.bookingListProgress);
        bookingListContainer = (LinearLayout) findViewById(R.id.bookingListContainer);

        showProgress(true);

        //Fetch bookings.
        fetchBookings();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("bookingList", "onmenuitem click. " + item.getItemId() + "," + R.id.home);
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
        Log.d("driverFragment", "Fetch bookings for driver: " + driverUId);
        RequestParams bookingListParameters = new RequestParams();
        bookingListParameters.put("driverUid", driverUId);
        bookingListParameters.put("tag", "vrm_get_driver_bookings");

        //Async Driverlist fetch.
        AsyncHttpService.get(Url.apiBaseUrl, bookingListParameters, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray.
                Log.d("driverFragment", "Parsing getDriverBookings() response: " + response);

                try {

                    if (response.getString("success") != null && response.getString("success").equals("1")) {

                        //If response is success, build Models from Json.
                        allBookings = new ArrayList<Booking>();
                        completedBookings = new ArrayList<Booking>();
                        currentBookings = new ArrayList<Booking>();
                        cancelledBookings = new ArrayList<Booking>();
                        rejectedBookings = new ArrayList<Booking>();

                        JSONObject jsonNewBookingList = response.has("newBookings") ? response.getJSONObject("newBookings") : null;
                        JSONObject jsonCompleteBookingList = response.has("completeBookings") ? response.getJSONObject("completeBookings") : null;
                        JSONObject jsonRejectedBookingList = response.has("rejectedBookings") ? response.getJSONObject("rejectedBookings") : null;

                        //Decode new bookings.
                        Log.d(logTag, "Decoding new bookings. ");
                        if (jsonNewBookingList != null) {
                            Iterator<String> keys = jsonNewBookingList.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject jsonBooking = jsonNewBookingList.getJSONObject(key);
                                Booking booking = Booking.decodeBookingListResponseJson(jsonBooking);

                                allBookings.add(booking);
                                currentBookings.add(booking);
                            }
                        }

                        //Decode completed bookings.
                        Log.d(logTag, "Decoding completed bookings. ");
                        if (jsonCompleteBookingList != null) {
                            Iterator<String> keys = jsonCompleteBookingList.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject jsonBooking = jsonCompleteBookingList.getJSONObject(key);
                                Booking booking = Booking.decodeBookingListResponseJson(jsonBooking);

                                allBookings.add(booking);
                                completedBookings.add(booking);
                            }
                        }

                        //Decode rejected bookings.
                        Log.d(logTag, "Decoding rejected bookings. ");
                        if (jsonRejectedBookingList != null) {
                            Iterator<String> keys = jsonRejectedBookingList.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject jsonBooking = jsonRejectedBookingList.getJSONObject(key);
                                Booking booking = Booking.decodeBookingListResponseJson(jsonBooking);

                                allBookings.add(booking);
                                rejectedBookings.add(booking);
                            }
                        }
                        showProgress(false);

                        //Setup TabLayout.
                        setupTablayout();
                    }
                } catch (JSONException ex) {
                    Log.e(logTag, "Failed to parse response. ");
                    Log.e(logTag, Log.getStackTraceString(ex));
                }
            }
        });
    }

    public void setupTablayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.addTab(tabLayout.newTab().setText("Past"));
        tabLayout.addTab(tabLayout.newTab().setText("Reject"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.bookingListViewPager);
        final BookingsPagerAdapter adapter = new BookingsPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            bookingListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            bookingListContainer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    bookingListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            bookingListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class BookingsPagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public BookingsPagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    DriverBookingsAllFragment fragment = new DriverBookingsAllFragment();
                    fragment.setBookings(allBookings);
                    return fragment;
                case 1:
                    DriverBookingsNewFragement newFragment = new DriverBookingsNewFragement();
                    newFragment.setBookings(currentBookings);
                    return newFragment;
                case 2:
                    DriverBookingsCompletedFragment completedFragment = new DriverBookingsCompletedFragment();
                    completedFragment.setBookings(completedBookings);
                    return completedFragment;
                case 3:
                    DriverBookingsRejectedFragment rejectedFragment = new DriverBookingsRejectedFragment();
                    rejectedFragment.setBookings(rejectedBookings);
                    return rejectedFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}

