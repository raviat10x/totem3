package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.Customer;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;
import com.move10x.totem.services.AsyncImageLoaderService;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LeadDetailsActivity extends Move10xActivity {

    private static final String TAG = "CustDetailsActivity";
    Customer customerDetails;
    ProgressBar progressBar;
    private LinearLayout customerDetailsContainer;
    private ImageView imgCustomerImage;
    private AppCompatButton btnCustomerViewBookings;
    private ImageButton btnCall;
    private TextView txtCustomerName;
//    private TextView txtLandline;
    private TextView txtMobileNumber;
    private TextView txtCustomerRegion;
    private TextView txtAddress;
    private TextView txtDOB1;
    private TextView txtAnnivarsary1;
    private TextView txtfvrtVehicle;
    private TextView txtAppVersion;
    private TextView txtCustomerCity;
    private TextView txtCustomerPin;
    private TextView txtCustomerCompany;
    private TextView txtLandlineNumber;
    private TextView txtBillingPref;
    private TextView txtCustomerEmail;
    private TextView txtLandline1;
//    private TextView txtCustomerCurrentLocation;
    private AppCompatButton btnViewBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Lead Details");


        //Read xml elements.
        customerDetailsContainer = (LinearLayout) findViewById(R.id.customerDetailsContainer);
        progressBar = (ProgressBar) findViewById(R.id.customerDetailsProgress);
        imgCustomerImage = (ImageView) findViewById(R.id.imgCustomerImage);
        btnCall = (ImageButton) findViewById(R.id.btnCall);
        txtCustomerName = (TextView) findViewById(R.id.txtCustomerName);
        txtMobileNumber = (TextView) findViewById(R.id.txtMobileNumber1);
        txtCustomerRegion = (TextView) findViewById(R.id.txtCustomerRegion);
        txtCustomerCity = (TextView)findViewById(R.id.txtCustomerCity);
        txtAddress = (TextView) findViewById(R.id.txtAddress1);
        txtDOB1 = (TextView) findViewById(R.id.txtDOB1);
        txtAnnivarsary1 = (TextView) findViewById(R.id.txtAnnivarsary1);
        txtfvrtVehicle = (TextView) findViewById(R.id.txtfvrtVehicle1);
        txtAppVersion = (TextView) findViewById(R.id.txtAppVersion);
        txtCustomerPin = (TextView)findViewById(R.id.txtCustomerPin);
        txtCustomerCompany = (TextView)findViewById(R.id.txtCustomerCompany);
//        btnViewBookings = (AppCompatButton)findViewById(R.id.btnViewBookings);
        txtBillingPref = (TextView)findViewById(R.id.txtBillingPref);
        txtCustomerEmail = (TextView)findViewById(R.id.txtCustomerEmail);
        txtLandline1 = (TextView)findViewById(R.id.txtLandline1);
//        txtCustomerCurrentLocation = (TextView)findViewById(R.id.txtCustomerCurrentLocation);



//        btnViewBookings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "On Driver container click.");
//                Intent intent = new Intent(getApplicationContext(), CustomerBookingListActivity.class);
//                intent.putExtra("uid", customerDetails.getUniqueId());
//                Log.d(TAG, "This is customer unique Id : " + customerDetails.getUniqueId());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Log.d("customerDetails", "Customer UID: " + customerDetails.getUniqueId());
//                startActivity(intent);
//            }
//        });

        imgCustomerImage.setAlpha(500);

        //Read driver details and set driver details in view.
        final String uId = getIntent().getStringExtra("customerUid");
//        Log.d(TAG, "UniqueId is : " +uId);
        getCustomerDetails(uId);

        imgCustomerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Inside buttonImage");
                imgCustomerImage.setAlpha(1000);
            }
        });

        //Floating call button.
        ImageButton fab = (ImageButton) findViewById(R.id.btnCall1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG, "On call button click.");
                String number = "tel:" + customerDetails.getMobile();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                startActivity(callIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getCustomerDetails(final String customerUid) {
        //Request Parameters.
//        Log.d(TAG, "Fetch Customer details of crm: " + customerUid);
        showProgress(true);

        final RequestParams customerDetailsParameters = new RequestParams();

        final String uId = getIntent().getStringExtra("customerUid");
//        Log.d(TAG, "UniqueId is : " +uId);
        customerDetailsParameters.put("customerUid", uId);
        customerDetailsParameters.put("tag", "userDetails");
//        Log.d(TAG, "CustomerUid is : " + customerUid);


//      Async Driverlist fetch.
        AsyncHttpService.post(Url.totemApiUrl, customerDetailsParameters, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray.
//                Log.d(TAG, "Parsing getCustomerDetails");
                try {
//                    Log.d(TAG, "getCustomerDetails() response:  " + response.toString());
                    if (response.getString("success") != null && response.getString("success").equals("1")) {
//                    Log.d(TAG, "Inside try Block and getting response");
                        JSONObject jsoncustomerDetails = response.getJSONObject("msg");

                        Log.i("Json from Server : ",""+jsoncustomerDetails.toString());
                        customerDetails = Customer.decodeJsonForDetails(jsoncustomerDetails);
                        txtCustomerName.setText(customerDetails.getFirstName() + " " + customerDetails.getLastName());
                        txtMobileNumber.setText(customerDetails.getMobile());
                        txtAddress.setText(customerDetails.getAddress() + ", " + customerDetails.getArea() + ", " + customerDetails.getCity());
                        txtCustomerCity.setText(customerDetails.getCity());
//                        txtLandline.setText(customerDetails.getLandLine());
                        txtCustomerRegion.setText(customerDetails.getArea());
                        txtfvrtVehicle.setText(customerDetails.getFvrtVehicle());
                        txtDOB1.setText(customerDetails.getDob());
                        txtAnnivarsary1.setText(customerDetails.getAnnivarsary());
                        txtCustomerPin.setText(customerDetails.getPin());
                        txtCustomerCompany.setText(customerDetails.getCompanyName());
//                        txtLandlineNumber.setText(customerDetails.getLandLine());
                        txtBillingPref.setText(customerDetails.getBillName());
                        txtCustomerEmail.setText(customerDetails.getEmail());
                        txtLandline1.setText(customerDetails.getlandline());
//                        txtCustomerCurrentLocation.setText(customerDetails.getCustomerLocation().toString());


//                        Log.d(TAG, "Customer Details are here : " + jsoncustomerDetails.toString());

                        //Set image circular
//                        Log.d(TAG, "http://www.move10x.com/assets/customer/vCard/" + customerDetails.getUniqueId());
                        AsyncImageLoaderService task = new AsyncImageLoaderService(imgCustomerImage, "http://www.move10x.com/assets/customer/vCard/" + customerDetails.getUniqueId()+ ".jpeg");
                        task.execute();
                    }
                } catch (JSONException ex) {
//                    Log.e(TAG, "Failed to parse response. Exception:" + ex.getMessage());
                }/* catch (ParseException ex) {
                    Log.e(TAG, "Failed to parse response. Exception:" + ex.getMessage());
                }*/ catch (Exception e) {
                    e.printStackTrace();
                }
                showProgress(false);
            }
        });
    }

    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            customerDetailsContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            customerDetailsContainer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    customerDetailsContainer.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

//            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//            progressBar.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });

        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            customerDetailsContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
