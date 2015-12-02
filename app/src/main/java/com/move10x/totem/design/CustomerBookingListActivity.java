package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.Booking;
import com.move10x.totem.models.CurrentProfile;
import com.move10x.totem.models.Customer;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;
import com.move10x.totem.services.CurrentProfileService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CustomerBookingListActivity extends Move10xActivity {


    private static final String TAG = "Customer Booking List";
    private String customerUId;
    ProgressBar progressBar;
    private ListView customerBookingList;
    private LinearLayout customerBookingListContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_booking_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Booking History");

        customerUId = getIntent().getStringExtra("uid");
        Log.d(TAG, "Customer Unique Id is : " + customerUId);
        progressBar = (ProgressBar) findViewById(R.id.bookingListProgress);
        customerBookingListContainer = (LinearLayout) findViewById(R.id.customerBookingListContainer);
        customerBookingList = (ListView) findViewById(R.id.customerBookingList);
        showProgress(true);
        fetchBookings();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            Log.d(TAG, "Inside showProgress()");

            customerBookingListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            customerBookingListContainer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    customerBookingListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
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
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            customerBookingListContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "At Resume.");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LeadDetailsActivity.class);
        startActivity(intent);
    }

    private void fetchBookings() {
        //Request Parameters.
//        String uid = customerUId;
        customerUId = "5619ef22baa1f9.42727973";
        String uid = customerUId;
        Log.d(TAG, "It might be wrong" + uid);
        Log.d(TAG, "Fetch Bookings of customerId: " + uid);
        RequestParams requestParameters = new RequestParams();
        requestParameters.put("uid", uid);
        requestParameters.put("tag", "bookingList");

        AsyncHttpService.post(Url.totemApiUrl, requestParameters, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray.
                Log.d(TAG, "Parsing response:  " + response);
                try {
                    Log.d(TAG, "Inside try block: " + response);
                    if (response.getString("success") != null && response.getString("success").equals("1")) {
                        Log.d(TAG, "Inside Success");
                        JSONArray jsonCustomerBookingList = response.getJSONArray("msg");
                        List<Booking> booking = new ArrayList<Booking>();
                        for (int i = 0; i < jsonCustomerBookingList.length(); i++) {
                            booking.add(Booking.decodeJsonForCustomerBookingList(jsonCustomerBookingList.getJSONObject(i)));
                        }
                        customerBookingList.setAdapter(new customerBookingListAdapter(getApplicationContext(), booking));
                    }
                } catch (JSONException ex) {
                    Log.d(TAG, "Inside try block");
                }
                Log.d(TAG, "After try catch block ProgressBar is going to be Invisible");
                showProgress(false);
                customerBookingListContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onMenuItem click. " + item.getItemId() + "," + R.id.home);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class customerBookingListAdapter extends BaseAdapter {
        private List<Booking> customerBookingList;
        private LayoutInflater inflater = null;

        public customerBookingListAdapter(Context applicationContext, List<Booking> customerBookingList) {
            inflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.customerBookingList = customerBookingList;
        }

        @Override
        public int getCount() {
            return customerBookingList.size();
        }

        @Override
        public Object getItem(int position) {
            return customerBookingList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Booking currentBooking = this.customerBookingList.get(position);
            View rowView = inflater.inflate(R.layout.customer_booking_list, null);

            TextView txtBookingDate = (TextView) rowView.findViewById(R.id.bookingDate);
            txtBookingDate.setText("Date : " + currentBooking.getDate());
            Log.d(TAG, currentBooking.getDate());

            TextView txtBookingRef = ((TextView) rowView.findViewById(R.id.bookingRef));
            txtBookingRef.setText("Reference : " + currentBooking.getReference());
            Log.d(TAG, currentBooking.getReference());

            TextView txtBookingStatus = ((TextView) rowView.findViewById(R.id.bookingStatus));
            if (currentBooking.getStatus().equals("complete")) {
                txtBookingStatus.setTextColor(Color.parseColor("#00A877"));
            } else if (currentBooking.getStatus().equals("canceled")) {
                txtBookingStatus.setTextColor(Color.parseColor("#C40233"));
            } else {
                txtBookingStatus.setTextColor(Color.parseColor("#1974D2"));
            }
            txtBookingStatus.setText("Status : " + currentBooking.getStatus());
            Log.d(TAG, currentBooking.getStatus());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            LinearLayout customerDetailsContainer = (LinearLayout) rowView.findViewById(R.id.customerDetailsContainer);
            customerDetailsContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "On Customer container click.");
                    Intent intent = new Intent(getApplicationContext(), CustomerBookingDetailsActivity.class);
                    intent.putExtra("bookingRef", currentBooking.getReference());
                    Log.d(TAG, "This is currentBookingreference : " + currentBooking.getReference());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            return rowView;

        }
    }
}