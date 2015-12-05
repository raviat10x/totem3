package com.move10x.totem.design;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.CurrentProfile;
import com.move10x.totem.models.Customer;
import com.move10x.totem.models.Driver;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;
import com.move10x.totem.services.CurrentProfileService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class AddTrainingActivity extends AppCompatActivity {

    private static final String TAG = "AddTrainingActivity";
    private EditText inputName;
    private EditText inputTrainingRemarks;
    private Button btnTrainingDate;
    private AppCompatButton btnSaveTraining;
    private Date trainingDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Training");

        inputName = (EditText) findViewById(R.id.inputName);
        inputTrainingRemarks = (EditText) findViewById(R.id.inputTrainingRemarks);
        btnTrainingDate = (Button) findViewById(R.id.btnTrainingDate);
        btnSaveTraining = (AppCompatButton) findViewById(R.id.btnSaveTraining);

        RegisterButtonClickEvents();

    }

    private void RegisterButtonClickEvents() {
        btnTrainingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnSaveTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddTraining();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//      Log.d("customerDetails", "onmenuitem click. " + item.getItemId() + "," + R.id.home);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onAddTraining() {

        Log.d(TAG, "onAddTrainingClick");

        String name = inputName.getText().toString();
        //Create new user.

        Intent intent = new Intent(this, AddTrainingActivity.class);
        intent.putExtra("name", name);
        finish();
//        inputTrainingRemarks.setText(inputTrainingRemarks.getText().toString());
//        btnTrainingDate.setText(btnTrainingDate.getText().toString());

//        Driver newDriver = new Driver();
//        newDriver.setFirstName(inputName.getText().toString().trim());
//        newDriver.setRemarks(inputTrainingRemarks.getText().toString().trim());
//        newDriver.setJoinDate(btnTrainingDate.getText().toString().trim());
//
//        CurrentProfile cp = (new CurrentProfileService(getApplicationContext())).getCurrentProfile();
//
//        RequestParams requestParams = new RequestParams();
//        requestParams.put("Name", inputName.getText().toString().trim());
//        requestParams.put("remarks", inputTrainingRemarks.getText().toString().trim());
//        if (trainingDate == null) {
//            requestParams.put("date", "");
//        } else {
//            requestParams.put("date", (new SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(trainingDate.getTime())));
//        }
//        requestParams.put("tag", "addTraining");
//
//        AsyncHttpService.post(Url.registerDriverUrl, requestParams, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                super.onSuccess(statusCode, headers, response);
//                Log.d(TAG, "At create driver success response: " + response);
//                try {
//                    JSONObject responseBody = (JSONObject) response.get(0);
//                    if (responseBody.has("result") && responseBody.getString("result").equals("fail")) {
//                        //Failure
//                        Toast.makeText(getBaseContext(), responseBody.getString("error_msg"), Toast.LENGTH_LONG);
//                    } else {
//                        //Success
//                        Log.d(TAG, "Driver created successfully. Uploading documents now.");
//                    }
//                } catch (JSONException ex) {
//                    Toast.makeText(getBaseContext(), "Internal error occured.", Toast.LENGTH_LONG);
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//                Toast.makeText(getBaseContext(), "Failed to reach server. Status: " + statusCode + ", json: " + errorResponse, Toast.LENGTH_LONG);
//                Log.d(TAG, "Failed to create driver. Status: " + statusCode + ", Server Response: " + errorResponse);
//            }
//        });

    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(onDateSet);
        date.show(getSupportFragmentManager(), "Date Picker");

    }

    DatePickerDialog.OnDateSetListener onDateSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            trainingDate = calendar.getTime();
            String dateText = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
            btnTrainingDate.setText(dateText);
        }
    };


}

