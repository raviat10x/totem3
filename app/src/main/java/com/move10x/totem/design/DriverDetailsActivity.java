package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.Driver;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;
import com.move10x.totem.services.AsyncImageLoaderService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class DriverDetailsActivity extends Move10xActivity {

    String logTag = "driverDetailsActivity";
    Driver driverDetails;
    LinearLayout driverDetailsContainer;
    ProgressBar progressBar;
    ImageView imgDriverImage;
    TextView txtDriverName, txtAuthority, txtMobileNumber, txtRegion, txtBaseStation;
    TextView txtVehicleBrand, txtRegestrationNo, txtDevice, txtAppVersion;
    TextView txtDutyStatus, txtVehicleCategory, txtPlan, txtRemarksDate;
    EditText inputRemarks;
    AppCompatButton btnViewBookings, btnUpdateDriverRemarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Driver Details");


        //Read xml elements.
        driverDetailsContainer = (LinearLayout) findViewById(R.id.driverDetailsContainer);
        progressBar = (ProgressBar) findViewById(R.id.driverDetailsProgress);
        btnViewBookings = (AppCompatButton) findViewById(R.id.btnViewBookings);
        btnUpdateDriverRemarks = (AppCompatButton) findViewById(R.id.btnUpdateRemarks);
        imgDriverImage = (ImageView) findViewById(R.id.imgDriverImage);
        txtDriverName = (TextView) findViewById(R.id.txtDriverName);
        txtAuthority = (TextView) findViewById(R.id.txtAuthority);
        txtMobileNumber = (TextView) findViewById(R.id.txtMobileNumber);
        txtRegion = (TextView) findViewById(R.id.txtRegion);
        txtBaseStation = (TextView) findViewById(R.id.txtBaseStation);
        txtVehicleCategory = (TextView) findViewById(R.id.txtVehicleCategory);
        txtVehicleBrand = (TextView) findViewById(R.id.txtVehicleBrand);
        txtRegestrationNo = (TextView) findViewById(R.id.txtVehicleRegNo);
        txtDevice = (TextView) findViewById(R.id.txtDevice);
        txtAppVersion = (TextView) findViewById(R.id.txtAppVersion);
        txtPlan = (TextView) findViewById(R.id.txtPlan);
        txtDutyStatus = (TextView) findViewById(R.id.txtDutyStatus);
        inputRemarks = (EditText) findViewById(R.id.txtRemarks);
        txtRemarksDate = (TextView) findViewById(R.id.txtRemarksDate);

        //Read driver details and set driver details in view.
        String uId = getIntent().getStringExtra("driverUid");
        getDriverDetails(uId);

        //Floating call button.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnCall);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "tel:" + driverDetails.getMobileNo();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                startActivity(callIntent);
            }
        });

        //On view bookings click.
        btnViewBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("driverDetails", "On Driver container click.");
                Intent intent = new Intent(getApplicationContext(), DriverBookingList.class);
                intent.putExtra("driverUid", driverDetails.getuId());
                Log.d("driverDetails", "Driver UID: " + driverDetails.getuId());
                startActivity(intent);
            }
        });

        //On update remarks click.
        btnUpdateDriverRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updteDriverRemarks();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("driverDetails", "onmenuitem click. " + item.getItemId() + "," + R.id.home);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getDriverDetails(String driverUid) {
        //Request Parameters.
        Log.d("driverFragment", "Fetch driver details of driver  for user: " + driverUid);
        showProgress(true);
        final RequestParams driverDetailsParameters = new RequestParams();
        driverDetailsParameters.put("driverUid", driverUid);
        driverDetailsParameters.put("tag", "vrm_get_driver_details");

        //Async Driverlist fetch.
        AsyncHttpService.get(Url.apiBaseUrl, driverDetailsParameters, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray.
                Log.d("driverFragment", "Parsing getDriveDetails() response. ");
                try {
                    Log.d("driverFragment", "getDriveDetails() response:  " + response.toString());
                    if (response.getString("success") != null && response.getString("success").equals("1")) {

                        JSONObject jsonDriverDetails = response.getJSONObject("driverDetails");
                        driverDetails = Driver.decodeJsonForDetails(jsonDriverDetails);
                        txtPlan.setText(driverDetails.getPlan());
                        txtDutyStatus.setText(driverDetails.getDutyStatus());
                        txtVehicleCategory.setText(driverDetails.getCategory());
                        txtDriverName.setText(driverDetails.getFirstName() + " " + driverDetails.getLastName());
                        txtAuthority.setText(driverDetails.getAuthority());
                        txtMobileNumber.setText(driverDetails.getMobileNo());
                        txtRegion.setText(driverDetails.getRegion());
                        txtBaseStation.setText(driverDetails.getBaseStation());
                        txtRegestrationNo.setText(driverDetails.getRegestrationNo());
                        txtVehicleBrand.setText(driverDetails.getTempoMake() + " " + driverDetails.getTempoModel());
                        txtDevice.setText(driverDetails.getAssignedMobile());
                        txtAppVersion.setText(driverDetails.getAppVersion());

                        if (!driverDetails.getRemarks().toString().trim().equals("")) {
                            inputRemarks.setText(driverDetails.getRemarks());
                            txtRemarksDate.setText(driverDetails.getRemarksTimeStamp());
                            /*Date commentDate = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse(driverDetails.getRemarksTimeStamp());
                            Calendar cal = Calendar.getInstance();
                            txtRemarksDate.setText("(" + commentDate.getDate() + "/" + commentDate.getMonth() + "/" + commentDate.getYear() + " " + commentDate.getHours() + ":" + commentDate.getMinutes() +")");*/
                        }

                        //Set image circular
                        AsyncImageLoaderService task = new AsyncImageLoaderService(imgDriverImage, driverDetails.getImagePath());
                        Log.d("driverDetails", driverDetails.getDutyStatus() + ", in view: " + txtDutyStatus.getText());

                        //Set duty status color based on status.
                        if (driverDetails.getDutyStatus().toLowerCase().equals("offduty")) {
                            //set red color
                            txtDutyStatus.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners_red));
                        } else if (driverDetails.getDutyStatus().toLowerCase().equals("available")) {
                            //set green color
                            txtDutyStatus.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners_green));
                        } else {
                            //set blue color
                            txtDutyStatus.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners_blue));
                        }
                        task.execute();

                    }
                } catch (JSONException ex) {
                    Log.e(logTag, "Failed to parse response. Exception:" + ex.getMessage());
                }/* catch (ParseException ex) {
                    Log.e(logTag, "Failed to parse response. Exception:" + ex.getMessage());
                }*/
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

    private void updteDriverRemarks() {

        Log.d(logTag, "On Update Driver remarks click.");
        showProgress(true);
        RequestParams requestParams = new RequestParams();
        requestParams.put("driverUid", driverDetails.getuId());
        requestParams.put("remarks", inputRemarks.getText().toString().trim());

        //Async Driverlist fetch.
        String url = Url.apiBaseUrl + "?tag=vrm_updte_driver_remarks";
        AsyncHttpService.post(url, requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray.
                Log.d(logTag, "Parsing getDriveDetails() response. ");
                try {
                    Log.d(logTag, "updteDriverRemarks() response:  " + response.toString());
                    if (response.getString("success") != null && response.getString("success").equals("1")) {
                        Toast.makeText(getApplicationContext(), "Remarks Updated.", Toast.LENGTH_LONG);
                        getDriverDetails(driverDetails.getuId());
                    }
                } catch (JSONException ex) {
                    Log.e(logTag, "Failed to parse json of getDriveDetails() response.");
                }

            }
        });
    }

    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            driverDetailsContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            driverDetailsContainer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    driverDetailsContainer.setVisibility(show ? View.GONE : View.VISIBLE);
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
            driverDetailsContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
