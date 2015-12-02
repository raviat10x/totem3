package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.Booking;
import com.move10x.totem.models.Customer;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.Booking;
import com.move10x.totem.models.Customer;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CustomerBookingDetailsActivity extends AppCompatActivity {

    private static final String TAG = "CustomerBookingDetails";
    private TextView txtCustomerName;
    Booking bookingDetails;
    private TextView txtBookingTime;
    private TextView txtBookingDate;
    private TextView txtPickLocation;
    private TextView txtDropLocation;
    private TextView txtReferenceId;
    private TextView txtDriverName;
    private TextView txtDriverMobile;
    private TextView txtTempoType;
    private TextView txtTotalFare;
    private TextView txtDiscount;
    private TextView txtTotalJourneyTime;
    private TextView txtTotalWaitingTime;
    private TextView txtChargableDistance;
    private TextView txtDriverFareShare;
    private TextView txtPaidToCompany;
    private TextView txtDriverRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_booking_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Booking Details");


        String TAG = "CBDetailsActivity";

        txtCustomerName = (TextView) findViewById(R.id.txtCustomerName);
        txtBookingDate  = (TextView) findViewById(R.id.txtBookingDate);
        txtPickLocation = (TextView) findViewById(R.id.txtPickLocation);
        txtDropLocation = (TextView) findViewById(R.id.txtDropLocation);
        txtReferenceId = (TextView) findViewById(R.id.txtReferenceId);
        txtDriverName = (TextView) findViewById(R.id.txtDriverName);
        txtDriverMobile = (TextView) findViewById(R.id.txtDriverMobile);
        txtTempoType = (TextView) findViewById(R.id.txtTempoType);
        txtTotalFare = (TextView) findViewById(R.id.txtTotalFare);
        txtDiscount = (TextView) findViewById(R.id.txtDiscount);
        txtTotalJourneyTime = (TextView)findViewById(R.id.txtTotalJourneyTime);
        txtTotalWaitingTime = (TextView)findViewById(R.id.txtTotalWaitingTime);
        txtChargableDistance = (TextView)findViewById(R.id.txtChargableDistance);
        txtDriverFareShare = (TextView)findViewById(R.id.txtDriverFareShare);
        txtPaidToCompany = (TextView)findViewById(R.id.txtPaidToCompany);
        txtDriverRating = (TextView)findViewById(R.id.txtDriverRating);
        txtBookingTime = (TextView)findViewById(R.id.txtBookingTime);
        ((CardView) findViewById(R.id.card_view)).setVisibility(View.VISIBLE);
        ((CardView) findViewById(R.id.card_view2)).setVisibility(View.VISIBLE);
        ((CardView) findViewById(R.id.card_view3)).setVisibility(View.VISIBLE);
        ((CardView) findViewById(R.id.card_view4)).setVisibility(View.VISIBLE);



        Log.d(TAG, "Before Getting Extra" + txtCustomerName);
        String bookingRef = getIntent().getStringExtra("bookingRef");
        Log.d(TAG, "After Getting Extra" + bookingRef);

        getBookingDetails(bookingRef);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onmMenuItem click. " + item.getItemId() + "," + R.id.home);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showProgress(final boolean show) {

    }


    public void getBookingDetails(final String bookingRef) {
        //Request Parameters.
        Log.d(TAG, "Fetch booking details of: " + bookingRef);
        showProgress(true);
        RequestParams bookingDetailsParameters = new RequestParams();

        bookingDetailsParameters.put("bookingRef", bookingRef);
        bookingDetailsParameters.put("tag", "bookingDetails");

        AsyncHttpService.post(Url.totemApiUrl, bookingDetailsParameters, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    if (response.getString("success") != null && response.getString("success").equals("1")) {
                        JSONObject jsonCustomerBookingDetails = response.getJSONObject("msg");

                        Log.i("Json from Server : ", "" + jsonCustomerBookingDetails.toString());
                        bookingDetails = Booking.decodeJsonForCustomerBookingDetails(jsonCustomerBookingDetails);
                        Log.d(TAG, "After  decoding" + jsonCustomerBookingDetails.toString());

                        txtCustomerName.setText(jsonCustomerBookingDetails.getString("user_id"));
                        txtBookingDate.setText(jsonCustomerBookingDetails.getString("booking_date"));
                        txtBookingTime.setText(jsonCustomerBookingDetails.getString("booking_time"));
                        txtPickLocation.setText(jsonCustomerBookingDetails.getString("pickup_lat"));
                        txtDropLocation.setText(jsonCustomerBookingDetails.getString("dest_long"));
                        txtReferenceId.setText(jsonCustomerBookingDetails.getString("booking_ref"));
                        txtDriverName.setText(jsonCustomerBookingDetails.getString("completed_name"));
//                        txtDriverMobile.setText(jsonCustomerBookingDetails.getString(""));
                        txtTempoType.setText(jsonCustomerBookingDetails.getString("tempo_type"));
                        txtTotalJourneyTime.setText(jsonCustomerBookingDetails.getString("total_journey_time"));
                        txtTotalWaitingTime.setText(jsonCustomerBookingDetails.getString("total_waiting_time"));
                        txtChargableDistance.setText(jsonCustomerBookingDetails.getString("chargeable_distance_km"));
                        txtTotalFare.setText(jsonCustomerBookingDetails.getString("total_fare"));
                        txtDiscount.setText(jsonCustomerBookingDetails.getString("total_discount"));
//                        txtDriverFareShare.setText(jsonCustomerBookingDetails.getString("driver_fare_share"));
//                        txtPaidToCompany.setText(jsonCustomerBookingDetails.getString("paid_to_company"));
                        txtDriverRating.setText(jsonCustomerBookingDetails.getString("driver_rating"));
//                        txtCustomerName = (TextView) findViewById(R.id.txtCustomerName);
//                        txtBookingDate  = (TextView) findViewById(R.id.txtBookingDate);
//                        txtPickLocation = (TextView) findViewById(R.id.txtPickLocation);
//                        txtDropLocation = (TextView) findViewById(R.id.txtDropLocation);
//                        txtReferenceId = (TextView) findViewById(R.id.txtReferenceId);
//                        txtDriverName = (TextView) findViewById(R.id.txtDriverName);
//                        txtDriverMobile = (TextView) findViewById(R.id.txtDriverMobile);
//                        txtTempoType = (TextView) findViewById(R.id.txtTempoType);
//                        txtTotalFare = (TextView) findViewById(R.id.txtTotalFare);
//                        txtDiscount = (TextView) findViewById(R.id.txtDiscount);

                    }
                } catch (JSONException ex)
                {
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                showProgress(false);
            }
        });
    }
}


