package com.move10x.totem.design;

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

public class BookingDetailsActivity extends Move10xActivity {
//nn
    LinearLayout bookingDetailsContainer;
    String logTag = "bookingDetailsActivity";
    ProgressBar progressBar;
    Booking bookingDetails;
    TextView txtReferenceId, txtTempoType, txtBookingStatus, txtPickupLocation, txtDropLocation, txtCustomerDetails;
    TextView txtTotalJourneyTime, txtTotalWaitingTime, txtChargableDistance;
    TextView txtTotalFare, txtDiscount, txtDriverFareShare, txtPaidToCompany, txtDriverRating, txtCustomerRating;
    TextView txtBookingCreatedAt;

    private TextView txtCustomerName;
    private TextView txtPickUpDate;
    private TextView txtTotalDistance;
    private TextView txtPickUpTime;
    private TextView txtBookingFrom;
    private TextView txtBookingUpTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Booking Details");

        bookingDetailsContainer = (LinearLayout) findViewById(R.id.bookingDetailsContainer);
        progressBar = (ProgressBar) findViewById(R.id.bookingDetailsProgress);

//        txtReferenceId = (TextView) findViewById(R.id.txtReferenceId);
        txtCustomerName = (TextView)findViewById(R.id.txtCustomerName);
        txtPickUpDate = (TextView)findViewById(R.id.txtPickUpDate);

        txtBookingStatus = (TextView) findViewById(R.id.txtBookingStatus);
        txtPickupLocation = (TextView) findViewById(R.id.txtPickupLocation);
        txtDropLocation = (TextView) findViewById(R.id.txtDropLocation);
//        txtBookingTime = (TextView) findViewById(R.id.txtBookingTime);
        txtTotalJourneyTime = (TextView) findViewById(R.id.txtTotalJourneyTime);
        txtTotalWaitingTime = (TextView) findViewById(R.id.txtTotalWaitingTime);
        txtTotalDistance = (TextView) findViewById(R.id.txtTotalDistance);
        txtChargableDistance = (TextView) findViewById(R.id.txtChargableDistance);
        txtTotalFare = (TextView) findViewById(R.id.txtTotalFare);
        txtDiscount = (TextView) findViewById(R.id.txtDiscount);
        txtDriverFareShare = (TextView) findViewById(R.id.txtDriverFareShare);
        txtPaidToCompany = (TextView) findViewById(R.id.txtPaidToCompany);
        txtDriverRating = (TextView) findViewById(R.id.txtDriverRating);
        txtCustomerRating = (TextView) findViewById(R.id.txtCustomerRating);
        txtBookingCreatedAt = (TextView) findViewById(R.id.txtBookingCreatedAt);
//        txtCustomerDetails = (TextView) findViewById(R.id.txtCustomerDetails);

        //Fetch bookingDetails.
        //Read driver details and set driver details in view.
        String bookingRef = getIntent().getStringExtra("bookingRef");
        getBookingDetails(bookingRef);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(logTag, "onmenuitem click. " + item.getItemId() + "," + R.id.home);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            bookingDetailsContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            bookingDetailsContainer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    bookingDetailsContainer.setVisibility(show ? View.GONE : View.VISIBLE);
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
            bookingDetailsContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void getBookingDetails(final String bookingRef) {
        //Request Parameters.
        Log.d(logTag, "Fetch booking details of: " + bookingRef);
        showProgress(true);
        RequestParams driverDetailsParameters = new RequestParams();
        driverDetailsParameters.put("bookingRef", bookingRef);
        driverDetailsParameters.put("tag", "vrm_get_booking_details");

        //Async Driverlist fetch.
        AsyncHttpService.get(Url.apiBaseUrl, driverDetailsParameters, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray.
                Log.d(logTag, "Parsing getBookingDetails() response. ");
                try {
                    Log.d(logTag, response.toString());
                    if (response.getString("success") != null && response.getString("success").equals("1")) {
//                        JSONObject jsonDriverDetails = response.getJSONObject("bookingDetails");
                        JSONObject jsonDriverDetails = response.getJSONObject("bookingDetails");

                        bookingDetails = Booking.decodeBookingDetailsResponseJson(jsonDriverDetails);
                        Log.d(logTag, "Customer Details: " + bookingDetails.getCustomer().toString());
//                        txtReferenceId.setText(bookingDetails.getReference());
                        txtCustomerName.setText(bookingDetails.getCustomer().getFirstName() + "" + bookingDetails.getCustomer().getLastName());
                        txtPickUpDate.setText(bookingDetails.getDate() + " " + bookingDetails.getTime());
                        txtBookingStatus.setText(bookingDetails.getStatus());
                        txtPickupLocation.setText(bookingDetails.getPickup());
                        txtDropLocation.setText(bookingDetails.getDrop());
//                        txtBookingTime.setText(bookingDetails.getBookingCreatedAt());
                        txtTotalJourneyTime.setText(bookingDetails.getTotalJourneyTime());
                        txtTotalWaitingTime.setText(bookingDetails.getTotalWaitingTime());
                        txtTotalDistance.setText(bookingDetails.getTotalDistance());
                        txtChargableDistance.setText(bookingDetails.getChargeableDistance());
                        txtTotalFare.setText(bookingDetails.getTotalFare());
                        txtDiscount.setText(bookingDetails.getDiscount());
                        txtDriverFareShare.setText(bookingDetails.getDriverFareShare());
                        txtPaidToCompany.setText(bookingDetails.getPaidToCompany());
                        txtDriverRating.setText(bookingDetails.getDriverRating());
                        txtCustomerRating.setText(bookingDetails.getCustomerRating());
                        txtBookingCreatedAt.setText(bookingDetails.getBookingCreatedAt());
                        Customer cust = bookingDetails.getCustomer();
//                        txtCustomerDetails.setText(cust.getFirstName() + " " + cust.getLastName() + " (" + cust.getMobile() + ")");
                    }
                } catch (JSONException ex) {
                    Log.e(logTag, "Failed to parse getBookingDetails() response json.");
                    Log.e(logTag, ex.getMessage());
                }
                showProgress(false);
            }

               /* @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    // Pull out the first event on the public timeline
                    JSONObject firstEvent = timeline.get(0);
                    String tweetText = firstEvent.getString("text");

                    // Do something with the response
                    System.out.println(tweetText);
                }*/
        });
    }
}
