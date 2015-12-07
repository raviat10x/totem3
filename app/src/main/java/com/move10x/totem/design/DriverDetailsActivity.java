package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.Driver;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;
import com.move10x.totem.services.AsyncImageLoaderService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    TextView txtDutyStatus, txtVehicleCategory, txtPlan, txtRemarksDate, txtRemarks,moreRemarksbutton;
    ImageButton btnUpdateDriverRemarks;
    AppCompatButton btnViewBookings;
//    AppCompatButton btnViewTrainings;

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
        btnUpdateDriverRemarks = (ImageButton) findViewById(R.id.btnUpdateRemarks);
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
        txtRemarks = (TextView) findViewById(R.id.txtRemarks);
        txtRemarksDate = (TextView) findViewById(R.id.txtRemarksDate);
        moreRemarksbutton =(TextView)findViewById(R.id.moreRemarks);
//        btnViewTrainings = (AppCompatButton)findViewById(R.id.btnViewTrainings);
        //Read driver details and set driver details in view.
        String uId = getIntent().getStringExtra("driverUid");
        getDriverDetails(uId);

        //setting the underline of the getMoreRemarks Button
        moreRemarksbutton.setPaintFlags(moreRemarksbutton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //Floating call button.
        ImageButton fab = (ImageButton) findViewById(R.id.btnCall);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(logTag, "On call button click.");
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
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.d("driverDetails", "Driver UID: " + driverDetails.getuId());
                startActivity(intent);
            }
        });

        moreRemarksbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moreRemarksIntent= new Intent(getApplicationContext(),RemarksActivity.class);
                moreRemarksIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(moreRemarksIntent);
            }
        });
//        btnViewTrainings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TrainingFragment fg = new TrainingFragment();
//                android.app.FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.driverDetailsContainer, fg);
//                Log.d(logTag, "Inside driverDetailsContainer" + driverDetailsContainer);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                Log.d(logTag, "Fragment Loaded Successfully.");
//
//            }
//        });

        //On update remarks click.
        btnUpdateDriverRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdteDriverRemarksClick();
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
                            txtRemarks.setText(driverDetails.getRemarks());
                            Date commentDate = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse(driverDetails.getRemarksTimeStamp());
                            txtRemarksDate.setText((new SimpleDateFormat("dd-MM-yyyy hh:mm")).format(commentDate));
                        } else {
                            txtRemarks.setText("None");
                            txtRemarksDate.setVisibility(View.GONE);
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
                }*/ catch (ParseException e) {
                    e.printStackTrace();
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

    private void onUpdteDriverRemarksClick() {

        //Show dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change driver remarks");
        final EditText input = new EditText(this);
        input.setSingleLine(false);
        input.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        input.setSelectAllOnFocus(true);
        input.setText(txtRemarks.getText());
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(logTag, "Response: " + input.getText().toString());
                showProgress(true);
                RequestParams requestParams = new RequestParams();
                requestParams.put("driverUid", driverDetails.getuId());
                requestParams.put("remarks", input.getText().toString().trim());

                //Async Driverlist fetch.
                String url = Url.apiBaseUrl + "?tag=vrm_updte_driver_remarks";
                AsyncHttpService.post(url, requestParams, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // If the response is JSONObject instead of expected JSONArray.
                        Log.d(logTag, "updteDriverRemarks() response:  " + response.toString());
                        try {
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
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
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
