package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.CurrentProfile;
import com.move10x.totem.models.Driver;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;
import com.move10x.totem.services.CurrentProfileService;
import com.move10x.totem.services.IOService;

import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class NewDriverActivity extends Move10xActivity {

    static final String File_Driver_Photo = "driverPic.jpeg";
    static final String File_Driving_License = "drivingLicense.jpeg";
    static final String File_Vehicle_Insurance = "vehicleInsurance.jpeg";
    static final String File_Vehicle_Front = "vehicleFront.jpeg";
    static final String File_Vehicle_Side = "vehicleSide.jpeg";
    static final String File_Vehicle_Back = "vehicleBack.jpeg";

    static final int INTENT_REQUEST_CAMERA_DRIVING_LICENSE = 1000;
    static final int INTENT_REQUEST_GALLERY_DRIVING_LICENSE = 1001;
    static final int INTENT_REQUEST_CAMERA_DRIVER_PHOTO = 1002;
    static final int INTENT_REQUEST_GALLERY_DRIVER_PHOTO = 1003;
    static final int INTENT_REQUEST_CAMERA_VEHICLE_INSURANCE = 1005;
    static final int INTENT_REQUEST_GALLERY_VEHICLE_INSURANCE = 1006;
    static final int INTENT_REQUEST_CAMERA_VEHICLE_FRONT = 1007;
    static final int INTENT_REQUEST_GALLERY_VEHICLE_FRONT = 1008;
    static final int INTENT_REQUEST_CAMERA_VEHICLE_SIDE = 1009;
    static final int INTENT_REQUEST_GALLERY_VEHICLE_SIDE = 1010;
    static final int INTENT_REQUEST_CAMERA_VEHICLE_BACK = 1011;
    static final int INTENT_REQUEST_GALLERY_VEHICLE_BACK = 1012;
    static final String logTag = "newDriverActivity";
    private int imageIndex = 0;

    private ScrollView newDriverContainer;
    private Button btnJoiningDate;
    private Date joiningDate = null;
    private AppCompatButton btnCreateDriver;
    private EditText inputFirstName, inputLastName, inputMobileNo, inputEmail, inputPersonalAddress, inputDriverRemarks;
    private EditText inputVehicleLenth, inputVehicleMake, inputVehicleModel, inputVehicleRegNo, inputVINNo, inputBaseSatation;

    private String vehicleCategory = "mini", plan = "freelance";

    //Resource for document uploads
    private TextView txtUploadDocumentsError;
    private ImageButton btnUploadDriverPhoto, btnUploadDrivingLicense, btnUploadVehicleInsurence;
    private ImageButton btnUploadVehicleFrontImage, btnUploadVehicleSideImage, btnUploadVehicleBackImage;
    private ImageView imgDriverPhoto, imgDrivingLicense, imgVehicleInsurence;
    private ImageView imgVehicleFrontImage, imgVehicleSideImage, imgVehicleBackImage;
    private RelativeLayout pnlDriverPhoto, pnlDrivingLicense, pnlVehicleInsurence;
    private RelativeLayout pnlVehicleFrontImage, pnlVehicleSideImage, pnlVehicleBackImage;

    private Spinner inputRegion, inputAuthority;
    private ProgressBar progressBar;
    private String[] regions;
    private String driverUniqueId;
    private String ftpServer, ftpUserName, ftpPassword, ftpDirectory;

    private AppCompatActivity currentActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_driver);

        setTitle("New Driver");

        //Read elements
        inputFirstName = (EditText) findViewById(R.id.inputFirstName);
        inputLastName = (EditText) findViewById(R.id.inputLastName);
        inputMobileNo = (EditText) findViewById(R.id.inputMobileNo);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPersonalAddress = (EditText) findViewById(R.id.inputPersonalAddress);
        inputVehicleLenth = (EditText) findViewById(R.id.inputVehicleLength);
        inputVehicleMake = (EditText) findViewById(R.id.inputVehicleMake);
        inputVehicleModel = (EditText) findViewById(R.id.inputVehicleModel);
        inputVehicleRegNo = (EditText) findViewById(R.id.inputVehicleRegestrationNumber);
        inputVINNo = (EditText) findViewById(R.id.inputVehicleVinNo);
        inputBaseSatation = (EditText) findViewById(R.id.inputBaseStation);
        inputRegion = (Spinner) findViewById(R.id.inputRegion);
        inputAuthority = (Spinner) findViewById(R.id.inputAurhority);
        inputDriverRemarks = (EditText) findViewById(R.id.inputDriverRemarks);
        //Read buttons
        btnJoiningDate = (Button) findViewById(R.id.btnJoiningDate);
        btnCreateDriver = (AppCompatButton) findViewById(R.id.btnSignup);

        //Read image uploads.
        txtUploadDocumentsError = (TextView) findViewById(R.id.txtUploadDocumentsError);
        imgDriverPhoto = (ImageView) findViewById(R.id.imgDriverPhoto);
        btnUploadDriverPhoto = (ImageButton) findViewById(R.id.btnUploadDriverPhoto);
        btnUploadDrivingLicense = (ImageButton) findViewById(R.id.btnUploadDrivingLicense);
        btnUploadVehicleInsurence = (ImageButton) findViewById(R.id.btnUploadVehicleInsurence);
        btnUploadVehicleFrontImage = (ImageButton) findViewById(R.id.btnUploadVehicleFrontImage);
        btnUploadVehicleSideImage = (ImageButton) findViewById(R.id.btnUploadVehicleSideImage);
        btnUploadVehicleBackImage = (ImageButton) findViewById(R.id.btnUploadVehicleBackImage);
        imgDriverPhoto = (ImageView) findViewById(R.id.imgDriverPhoto);
        imgDrivingLicense = (ImageView) findViewById(R.id.imgDrivingLicense);
        imgVehicleInsurence = (ImageView) findViewById(R.id.imgVehicleInsurence);
        imgVehicleFrontImage = (ImageView) findViewById(R.id.imgVehicleFrontImage);
        imgVehicleSideImage = (ImageView) findViewById(R.id.imgVehicleSideImage);
        imgVehicleBackImage = (ImageView) findViewById(R.id.imgVehicleBackImage);
        pnlDriverPhoto = (RelativeLayout) findViewById(R.id.pnlDriverPhoto);
        pnlDrivingLicense = (RelativeLayout) findViewById(R.id.pnlDrivingLicense);
        pnlVehicleInsurence = (RelativeLayout) findViewById(R.id.pnlVehicleInsurence);
        pnlVehicleFrontImage = (RelativeLayout) findViewById(R.id.pnlVehicleFrontImage);
        pnlVehicleSideImage = (RelativeLayout) findViewById(R.id.pnlVehicleSideImage);
        pnlVehicleBackImage = (RelativeLayout) findViewById(R.id.pnlVehicleBackImage);

        newDriverContainer = (ScrollView) findViewById(R.id.newDriverContainer);
        progressBar = (ProgressBar) findViewById(R.id.newDriverProgress);
        RegisterButtonClickEvents();

        //Fetch details required to add new driver.
        fetchRequiredInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(logTag, "At activity result. requestCode = " + requestCode + " resultCode = " + resultCode);

        // Driver Photo Response
        if (requestCode == INTENT_REQUEST_CAMERA_DRIVER_PHOTO && resultCode == RESULT_OK) {
            Log.d(logTag, "response came from Driver photo from camera.");

            //Retrieve Bitmap image.
            Bundle extras = data.getExtras();
            Bitmap bitmapDriverPhoto = getResizedBitmap((Bitmap) extras.get("data"));
            (new IOService()).writeImageToFile(File_Driver_Photo, (Bitmap) extras.get("data"));

            //Set image to vitmap view.
            imgDriverPhoto.setImageBitmap(bitmapDriverPhoto);
            imgDriverPhoto.invalidate();
            bitmapDriverPhoto = null;

            //Hide camera options.
            pnlDriverPhoto.setVisibility(View.VISIBLE);
            btnUploadDriverPhoto.setVisibility(View.GONE);


        } else if (requestCode == INTENT_REQUEST_GALLERY_DRIVER_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Log.d(logTag, "Response from driver photo from gallery.");

            android.net.Uri selectedImagePath = data.getData();
            Log.d(logTag, "URI: " + selectedImagePath.getPath());
            try {
                //Retrieve Bitmap image.
                Bitmap originalDriverPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath);
                Bitmap bitmapDriverPhoto = getResizedBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath));
                (new IOService()).writeImageToFile(File_Driver_Photo, originalDriverPhoto);

                //Set bitmap to imageview.
                imgDriverPhoto.setImageBitmap(bitmapDriverPhoto);
                imgDriverPhoto.invalidate();
                bitmapDriverPhoto = null;

                //Hide camera options.
                pnlDriverPhoto.setVisibility(View.VISIBLE);
                btnUploadDriverPhoto.setVisibility(View.GONE);

            } catch (FileNotFoundException e) {
                Log.e(logTag, e.getMessage());
            } catch (IOException e) {
                Log.e(logTag, e.getMessage());
            }
        }

        //Driving license response.
        if (requestCode == INTENT_REQUEST_CAMERA_DRIVING_LICENSE && resultCode == RESULT_OK) {
            Log.d(logTag, "response came from driving license from camera.");

            //Retrieve Bitmap image.
            Bundle extras = data.getExtras();
            Bitmap bitmapDrivingLicense = getResizedBitmap((Bitmap) extras.get("data"));
            (new IOService()).writeImageToFile(File_Driving_License, (Bitmap) extras.get("data"));

            //Set image to vitmap view.
            imgDrivingLicense.setImageBitmap(bitmapDrivingLicense);
            imgDrivingLicense.invalidate();
            bitmapDrivingLicense = null;

            //Hide camera options.
            pnlDrivingLicense.setVisibility(View.VISIBLE);
            btnUploadDrivingLicense.setVisibility(View.GONE);

        } else if (requestCode == INTENT_REQUEST_GALLERY_DRIVING_LICENSE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Log.d(logTag, "Response from driving license from gallery.");

            android.net.Uri selectedImagePath = data.getData();
            Log.d(logTag, "URI: " + selectedImagePath.getPath());
            try {
                //Retrieve Bitmap image.
                Bitmap originalDrivingLicense = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath);
                Bitmap bitmapDrivingLicense = getResizedBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath));
                (new IOService()).writeImageToFile(File_Driving_License, originalDrivingLicense);

                //Set image to bitmap view.
                imgDrivingLicense.setImageBitmap(bitmapDrivingLicense);
                imgDrivingLicense.invalidate();
                bitmapDrivingLicense = null;

                //Hide camera options.
                pnlDrivingLicense.setVisibility(View.VISIBLE);
                btnUploadDrivingLicense.setVisibility(View.GONE);

            } catch (FileNotFoundException e) {
                Log.e(logTag, e.getMessage());
            } catch (IOException e) {
                Log.e(logTag, e.getMessage());
            }
        }

        //Vehicle insureence response.
        if (requestCode == INTENT_REQUEST_CAMERA_VEHICLE_INSURANCE && resultCode == RESULT_OK) {
            Log.d(logTag, "response came from VEHICLE INSURANCE from camera.");

            //Retrieve Bitmap image.
            Bundle extras = data.getExtras();
            Bitmap bitmapVehicleInsurance = getResizedBitmap((Bitmap) extras.get("data"));
            (new IOService()).writeImageToFile(File_Vehicle_Insurance, (Bitmap) extras.get("data"));

            //Set image to vitmap view.
            imgVehicleInsurence.setImageBitmap(bitmapVehicleInsurance);
            imgVehicleInsurence.invalidate();
            bitmapVehicleInsurance = null;

            //Hide camera options.
            pnlVehicleInsurence.setVisibility(View.VISIBLE);
            btnUploadVehicleInsurence.setVisibility(View.GONE);

        } else if (requestCode == INTENT_REQUEST_GALLERY_VEHICLE_INSURANCE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Log.d(logTag, "Response from VEHICLE INSURANCE from gallery.");

            android.net.Uri selectedImagePath = data.getData();
            Log.d(logTag, "URI: " + selectedImagePath.getPath());
            try {
                //Retrieve Bitmap image.
                Bitmap orignalBitmapVehicleInsurance = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath);
                Bitmap bitmapVehicleInsurance = getResizedBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath));
                (new IOService()).writeImageToFile(File_Vehicle_Insurance, orignalBitmapVehicleInsurance);

                //Set image to vitmap view.
                imgVehicleInsurence.setImageBitmap(bitmapVehicleInsurance);
                imgVehicleInsurence.invalidate();
                bitmapVehicleInsurance = null;

                //Hide camera options.
                pnlVehicleInsurence.setVisibility(View.VISIBLE);
                btnUploadVehicleInsurence.setVisibility(View.GONE);

            } catch (FileNotFoundException e) {
                Log.e(logTag, e.getMessage());
            } catch (IOException e) {
                Log.e(logTag, e.getMessage());
            }
        }

        // Vehicle Front
        if (requestCode == INTENT_REQUEST_CAMERA_VEHICLE_FRONT && resultCode == RESULT_OK) {
            Log.d(logTag, "response came from vehicle front from camera.");

            //Retrieve Bitmap image.
            Bundle extras = data.getExtras();
            Bitmap bitmapVehicleFrontPhoto = getResizedBitmap((Bitmap) extras.get("data"));
            (new IOService()).writeImageToFile(File_Vehicle_Front, (Bitmap) extras.get("data"));

            //Set image to vitmap view.
            imgVehicleFrontImage.setImageBitmap(bitmapVehicleFrontPhoto);
            imgVehicleFrontImage.invalidate();
            bitmapVehicleFrontPhoto = null;

            //Hide camera options.
            pnlVehicleFrontImage.setVisibility(View.VISIBLE);
            btnUploadVehicleFrontImage.setVisibility(View.GONE);

        } else if (requestCode == INTENT_REQUEST_GALLERY_VEHICLE_FRONT && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Log.d(logTag, "Response from vehicle front from gallery.");

            android.net.Uri selectedImagePath = data.getData();
            Log.d(logTag, "URI: " + selectedImagePath.getPath());
            try {
                //Retrieve Bitmap image.
                Bitmap originalBitmapVehicleFrontPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath);
                Bitmap bitmapVehicleFrontPhoto = getResizedBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath));
                (new IOService()).writeImageToFile(File_Vehicle_Front, originalBitmapVehicleFrontPhoto);

                //Set bitmap to imageview.
                imgVehicleFrontImage.setImageBitmap(bitmapVehicleFrontPhoto);
                imgVehicleFrontImage.invalidate();
                bitmapVehicleFrontPhoto = null;

                //Hide camera options.
                pnlVehicleFrontImage.setVisibility(View.VISIBLE);
                btnUploadVehicleFrontImage.setVisibility(View.GONE);

            } catch (FileNotFoundException e) {
                Log.e(logTag, e.getMessage());
            } catch (IOException e) {
                Log.e(logTag, e.getMessage());
            }
        }

        // Vehicle Side
        if (requestCode == INTENT_REQUEST_CAMERA_VEHICLE_SIDE && resultCode == RESULT_OK) {
            Log.d(logTag, "response came from vehicle side from camera.");

            //Retrieve Bitmap image.
            Bundle extras = data.getExtras();
            Bitmap bitmapVehicleSidePhoto = getResizedBitmap((Bitmap) extras.get("data"));
            (new IOService()).writeImageToFile(File_Vehicle_Side, (Bitmap) extras.get("data"));

            //Set image to vitmap view.
            imgVehicleSideImage.setImageBitmap(bitmapVehicleSidePhoto);
            imgVehicleSideImage.invalidate();
            bitmapVehicleSidePhoto = null;

            //Hide camera options.
            pnlVehicleSideImage.setVisibility(View.VISIBLE);
            btnUploadVehicleSideImage.setVisibility(View.GONE);

        } else if (requestCode == INTENT_REQUEST_GALLERY_VEHICLE_SIDE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Log.d(logTag, "Response from vehicle side from gallery.");

            android.net.Uri selectedImagePath = data.getData();
            Log.d(logTag, "URI: " + selectedImagePath.getPath());
            try {
                //Retrieve Bitmap image.
                Bitmap originalBitmapVehicleSidePhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath);
                Bitmap bitmapVehicleSidePhoto = getResizedBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath));
                (new IOService()).writeImageToFile(File_Vehicle_Side, originalBitmapVehicleSidePhoto);

                //Set bitmap to imageview.
                imgVehicleSideImage.setImageBitmap(bitmapVehicleSidePhoto);
                imgVehicleSideImage.invalidate();
                bitmapVehicleSidePhoto = null;

                //Hide camera options.
                pnlVehicleSideImage.setVisibility(View.VISIBLE);
                btnUploadVehicleSideImage.setVisibility(View.GONE);

            } catch (FileNotFoundException e) {
                Log.e(logTag, e.getMessage());
            } catch (IOException e) {
                Log.e(logTag, e.getMessage());
            }
        }

        // Vehicle Back
        if (requestCode == INTENT_REQUEST_CAMERA_VEHICLE_BACK && resultCode == RESULT_OK) {
            Log.d(logTag, "response came from vehicle back from camera.");

            //Retrieve Bitmap image.
            Bundle extras = data.getExtras();
            Bitmap bitmapVehicleBackPhoto = getResizedBitmap((Bitmap) extras.get("data"));
            (new IOService()).writeImageToFile(File_Vehicle_Back, (Bitmap) extras.get("data"));

            //Set image to vitmap view.
            imgVehicleBackImage.setImageBitmap(bitmapVehicleBackPhoto);
            imgVehicleBackImage.invalidate();
            bitmapVehicleBackPhoto = null;

            //Hide camera options.
            pnlVehicleBackImage.setVisibility(View.VISIBLE);
            btnUploadVehicleBackImage.setVisibility(View.GONE);

        } else if (requestCode == INTENT_REQUEST_GALLERY_VEHICLE_BACK && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Log.d(logTag, "Response from vehicle side from gallery.");

            android.net.Uri selectedImagePath = data.getData();
            Log.d(logTag, "URI: " + selectedImagePath.getPath());
            try {
                //Retrieve Bitmap image.
                Bitmap originalBitmapVehicleBackPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath);
                Bitmap bitmapVehicleBackPhoto = getResizedBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath));
                (new IOService()).writeImageToFile(File_Vehicle_Back, originalBitmapVehicleBackPhoto);

                //Set bitmap to imageview.
                imgVehicleBackImage.setImageBitmap(bitmapVehicleBackPhoto);
                imgVehicleBackImage.invalidate();
                bitmapVehicleBackPhoto = null;

                //Hide camera options.
                pnlVehicleBackImage.setVisibility(View.VISIBLE);
                btnUploadVehicleBackImage.setVisibility(View.GONE);

            } catch (FileNotFoundException e) {
                Log.e(logTag, e.getMessage());
            } catch (IOException e) {
                Log.e(logTag, e.getMessage());
            }
        }
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
            joiningDate = calendar.getTime();
            String dateText = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
            btnJoiningDate.setText(dateText);
        }
    };

    private void OnCreateDriverClick() {
        Log.d(logTag, "OnDriverClick");

        imageIndex = 0;

        Boolean result = validateDriverFields();
        if (result) {

            //Create new user.
            Driver newDriver = new Driver();
            newDriver.setAuthority(inputAuthority.getSelectedItem().toString());
            newDriver.setFirstName(inputFirstName.getText().toString().trim());
            newDriver.setLastName(inputLastName.getText().toString().trim());
            newDriver.setMobileNo(inputMobileNo.getText().toString().trim());
            newDriver.setEmail(inputEmail.getText().toString().trim());
            newDriver.setAddress(inputPersonalAddress.getText().toString().trim());
            newDriver.setBaseStation(inputBaseSatation.getText().toString().trim());
            newDriver.setRegion(inputRegion.getSelectedItem().toString());
            newDriver.setCategory(vehicleCategory);
            newDriver.setVehicleLength(inputVehicleLenth.getText().toString());
            newDriver.setTempoMake(inputVehicleMake.getText().toString().trim());
            newDriver.setTempoModel(inputVehicleModel.getText().toString().trim());
            newDriver.setRegestrationNo(inputVehicleRegNo.getText().toString().trim());
            newDriver.setVinNo(inputVINNo.getText().toString().trim());
            newDriver.setJoinDate(new SimpleDateFormat("dd/MM/yyyy").format(joiningDate.getTime()));
            newDriver.setTempoModel(inputVehicleModel.getText().toString().trim());
            newDriver.setPlan(plan);

            //Create New Driver.
            showProgress(true);
            CurrentProfile cp = (new CurrentProfileService(getApplicationContext())).getCurrentProfile();

            //Request Parameters.
            RequestParams requestParams = new RequestParams();
            requestParams.put("uid", driverUniqueId);
            requestParams.put("region", inputRegion.getSelectedItem().toString());
            requestParams.put("firstname", inputFirstName.getText().toString().trim());
            requestParams.put("lastname", inputLastName.getText().toString().trim());
            String email = inputEmail.getText().toString().trim() == "" ? null : inputEmail.getText().toString().trim();
            requestParams.put("email", email);
            requestParams.put("username", inputMobileNo.getText().toString().trim());
            requestParams.put("tlength", inputVehicleLenth.getText().toString().trim());
            requestParams.put("tmake", inputVehicleMake.getText().toString().trim());
            requestParams.put("tmodel", inputVehicleModel.getText().toString().trim());
            requestParams.put("tcat", vehicleCategory);
            requestParams.put("treg", inputVehicleRegNo.getText().toString().trim());
            requestParams.put("tvin", inputVINNo.getText().toString().trim());
            requestParams.put("address", inputPersonalAddress.getText().toString().trim());
            requestParams.put("workStatus", "PENDING_VERIFY");
            requestParams.put("IMEI", "999999999999999");
            requestParams.put("basestation", inputBaseSatation.getText().toString().trim());
            requestParams.put("appversion", "");
            requestParams.put("joinDate", (new SimpleDateFormat("dd/MM/yyyy").format(joiningDate.getTime())) + " 00:00:00");
            requestParams.put("plan", plan);
            requestParams.put("vehicle_branded", "0");
            requestParams.put("authority", inputAuthority.getSelectedItem().toString().trim());
            requestParams.put("sourced_by", cp.getUserName());

            Log.d(logTag, "creating driver: " + newDriver.toString());


            //Async Driverlist fetch.
            AsyncHttpService.post(Url.registerDriverUrl, requestParams, new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d(logTag, "At create driver success response: " + response);
                    try {
                        JSONObject responseBody = (JSONObject)response.get(0);
                        if (responseBody.has("result") && responseBody.getString("result").equals("fail")) {
                            //Failure
                            Toast.makeText(getBaseContext(), responseBody.getString("error_msg"), Toast.LENGTH_LONG);
                        } else {
                            //Success
                            uploadDriverDocuments();
                        }
                    } catch (JSONException ex) {
                        Toast.makeText(getBaseContext(), "Internal error occured.", Toast.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Toast.makeText(getBaseContext(), "Failed to reach server. Status: " + statusCode + ", json: " + errorResponse, Toast.LENGTH_LONG);
                    Log.d(logTag, "Failed to create driver. Status: " + statusCode + ", Server Response: " + errorResponse);
                }

            });
        }
    }

    private void RegisterButtonClickEvents() {
        btnCreateDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCreateDriverClick();
            }

        });
        btnJoiningDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Upload Driver Photo
        btnUploadDriverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(logTag, "Show dialog for choosing driver photo.");
                showDriverPhotoImageSource();
            }
        });

        // Remove driver photo.
        pnlDriverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(logTag, "Remove driver photo.");
                pnlDriverPhoto.setVisibility(View.GONE);
                btnUploadDriverPhoto.setVisibility(View.VISIBLE);
            }
        });

        // Upload Driving License
        btnUploadDrivingLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(logTag, "Show dialog for choosing driving license.");
                showDrivingLicenseImageSource();
            }
        });

        // Remove Driving License.
        pnlDrivingLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(logTag, "Remove driver driving license.");
                pnlDrivingLicense.setVisibility(View.GONE);
                btnUploadDrivingLicense.setVisibility(View.VISIBLE);
            }
        });

        // Upload Vehicle Insurence
        btnUploadVehicleInsurence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(logTag, "Show dialog for choosing driving license.");
                showVehicleInsurenceImageSource();
            }
        });

        // Remove Vehicle Insurence
        pnlVehicleInsurence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(logTag, "Remove driver Vehicle Insurence.");
                pnlVehicleInsurence.setVisibility(View.GONE);
                btnUploadVehicleInsurence.setVisibility(View.VISIBLE);
            }
        });

        // Upload Vehicle Front Image
        btnUploadVehicleFrontImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(logTag, "Show dialog for choosing vehicle front image.");
                showVehicleFrontImageSource();
            }
        });

        // Remove Vehicle Front Image
        pnlVehicleFrontImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(logTag, "Remove driver Vehicle front image.");
                pnlVehicleFrontImage.setVisibility(View.GONE);
                btnUploadVehicleFrontImage.setVisibility(View.VISIBLE);
            }
        });

        // Upload Vehicle Side Image
        btnUploadVehicleSideImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(logTag, "Show dialog for choosing vehicle side image.");
                showVehicleSideImageSource();
            }
        });

        // Remove Vehicle Side Image
        pnlVehicleSideImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(logTag, "Remove driver Vehicle side image.");
                pnlVehicleSideImage.setVisibility(View.GONE);
                btnUploadVehicleSideImage.setVisibility(View.VISIBLE);
            }
        });

        // Upload Vehicle Back Image
        btnUploadVehicleBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(logTag, "Show dialog for choosing vehicle back image.");
                showVehicleBackImageSource();
            }
        });

        // Remove Vehicle Back Image
        pnlVehicleBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(logTag, "Remove driver Vehicle back image.");
                pnlVehicleBackImage.setVisibility(View.GONE);
                btnUploadVehicleBackImage.setVisibility(View.VISIBLE);
            }
        });

        //RadioButton Vehicle Category check change.
        ((RadioGroup) findViewById(R.id.inputTempoCategory)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedRadioButtonID = group.getCheckedRadioButtonId();
                vehicleCategory = ((RadioButton) group.findViewById(selectedRadioButtonID)).getText().toString().toLowerCase();
                if (selectedRadioButtonID == R.id.inputCategoryMaxPlus)
                    vehicleCategory = "maxplus";
                Log.d(logTag, "Selected vehicle category:" + vehicleCategory);
            }
        });

        //RadioButton plan check change.
        ((RadioGroup) findViewById(R.id.inputPlan)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedRadioButtonID = group.getCheckedRadioButtonId();
                plan = ((RadioButton) group.findViewById(selectedRadioButtonID)).getText().toString().toLowerCase();
            }
        });


    }

    private Boolean validateDriverFields() {

        //Clear previous errors.
        txtUploadDocumentsError.setVisibility(View.GONE);

        //Validate First Name
        inputFirstName.setError(null);
        String firstName = inputFirstName.getText().toString().trim();
        if (firstName.length() == 0) {
            inputFirstName.setError("Provide First Name");
            return false;
        } else if (firstName.length() > 100) {
            inputFirstName.setError("First Name is too long.");
            return false;
        } else if (!Pattern.compile("([A-Z]|[a-z]*)").matcher(firstName).find()) {
            inputFirstName.setError("Only characters are allowed.");
            return false;
        }

        //Validate Last Name
        inputLastName.setError(null);
        String lastName = inputLastName.getText().toString().trim();
        if (lastName.length() == 0) {
            inputLastName.setError("Provide Last Name");
            return false;
        } else if (lastName.length() > 100) {
            inputFirstName.setError("Last Name is too long.");
            return false;
        } else if (!Pattern.compile("([A-Z]|[a-z]*)").matcher(lastName).find()) {
            inputLastName.setError("Only characters are allowed.");
            return false;
        }

        //Validate Mobile Number
        inputMobileNo.setError(null);
        String mobileNo = inputMobileNo.getText().toString().trim();
        if (mobileNo.length() == 0) {
            inputMobileNo.setError("Provide Last Name");
            return false;
        } else if (mobileNo.length() != 10) {
            inputMobileNo.setError("Provide 10 digit mobile number.");
            return false;
        } else if (!Pattern.compile("^([0-9]*)$").matcher(mobileNo).find()) {
            inputMobileNo.setError("Only numbers are allowed.");
            return false;
        }

        //Vallidate Email
        /*inputEmail.setError(null);
        String emailAddress = inputEmail.getText().toString().trim();
        if (emailAddress.length() != 0 && !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            inputEmail.setError("Invalid Email Addresss.");
            return false;
        }*/

        //Personal Address
        inputPersonalAddress.setError(null);
        String personalAddress = inputPersonalAddress.getText().toString().trim();
        if (personalAddress.length() == 0) {
            inputPersonalAddress.setError("Provide Address.");
        } else if (personalAddress.length() > 150) {
            inputPersonalAddress.setError("Too long driver address.");
            return false;
        }

        //Validate Vehicle Make
        inputVehicleMake.setError(null);
        String vehicleMake = inputVehicleMake.getText().toString().trim();
        if (vehicleMake.length() == 0) {
            inputVehicleMake.setError("Provide vehicle make.");
            return false;
        } else if (vehicleMake.length() > 30) {
            inputVehicleMake.setError("Too long vehicle make.");
            return false;
        }

        //Validate Vehicle Length
        inputVehicleLenth.setError(null);
        String vehicleLength = inputVehicleLenth.getText().toString().trim();
        if (vehicleLength.length() == 0) {
            inputVehicleLenth.setError("Provide vehicle length.");
            return false;
        }
        if (vehicleLength.length() > 5) {
            inputVehicleLenth.setError("Invalid vehicle length.(Valid Pattern: 00.00 )");
            return false;
        } else {

            String regexDecimal = "^\\d\\.\\d$";
            String regexInteger = "^\\d$";
            String regexDouble = regexDecimal + "|" + regexInteger;
            if (!Pattern.compile(regexDouble).matcher(vehicleLength).find()) {
                inputVehicleLenth.setError("Invalid length. (Valid Pattern: 0.0 or 0 )");
                return false;
            }
        }

        //validate Vehicle Model
        inputVehicleModel.setError(null);
        String vehicleModel = inputVehicleModel.getText().toString().trim();
        if (vehicleModel.length() == 0) {
            inputVehicleModel.setError("Provide Vehicle Model.");
            return false;
        } else if (vehicleModel.length() > 30) {
            inputVehicleModel.setError("Too long vehicle model.");
            return false;
        }

        //Validate Vehicle Number Plate
        inputVehicleRegNo.setError(null);
        String vehicleRegNo = inputVehicleRegNo.getText().toString().trim();
        if (vehicleRegNo.length() == 0) {
            inputVehicleRegNo.setError("Provide Regestration No.");
            return false;
        } else if (vehicleRegNo.length() > 12) {
            inputVehicleRegNo.setError("Too long Regestration No.");
            return false;
        }

        //Validate VIN No
        inputVINNo.setError(null);
        String vinNo = inputVINNo.getText().toString().trim();
        if (vinNo.length() == 0) {
            inputVINNo.setError("Provide VIN No.");
            return false;
        } else if (vinNo.length() > 20) {
            inputVINNo.setError("Too long Vin No.");
            return false;
        }

        //Validate JoiningDate
        btnJoiningDate.setError(null);
        if (joiningDate == null) {
            btnJoiningDate.setError("Provide Joining Date.");
            return false;
        }

        //Check if all documents are uploaded.
        pnlDriverPhoto.setVisibility(View.VISIBLE);
        btnUploadDriverPhoto.setVisibility(View.GONE);
        if (btnUploadDriverPhoto.getVisibility() == View.VISIBLE || btnUploadDrivingLicense.getVisibility() == View.VISIBLE ||
                btnUploadVehicleInsurence.getVisibility() == View.VISIBLE || btnUploadVehicleFrontImage.getVisibility() == View.VISIBLE ||
                btnUploadVehicleSideImage.getVisibility() == View.VISIBLE || btnUploadVehicleBackImage.getVisibility() == View.VISIBLE) {
            //show error message
            txtUploadDocumentsError.setVisibility(View.VISIBLE);
            return false;
        }

        Log.d(logTag, "Validation complete all fields are valid.");
        return true;
    }

    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            newDriverContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            newDriverContainer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    newDriverContainer.setVisibility(show ? View.GONE : View.VISIBLE);
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
            newDriverContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void fetchRequiredInfo() {

        //Request Parameters.
        Log.d(logTag, "Fetch details of new driver.");
        showProgress(true);
        RequestParams requestParams = new RequestParams();
        requestParams.put("tag", "vrm_get_details_for_new_driver");

        //Async Driverlist fetch.
        AsyncHttpService.get(Url.apiBaseUrl, requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray.
                Log.d(logTag, "Parsing fetchNewDriverInfo() response. ");
                try {
                    Log.d(logTag, "fetchNewDriverInfo() response:  " + response.toString());
                    if (response.getString("success") != null && response.getString("success").equals("1")) {

                        //Get unique id
                        driverUniqueId = response.getString("uid");

                        //Get regions
                        JSONArray rawJsonArray = response.getJSONArray("regions");
                        regions = new String[rawJsonArray.length()];
                        for (int i = 0; i < rawJsonArray.length(); i++) {
                            regions[i] = rawJsonArray.getString(i);
                        }
                        //Set adapter with regions.
                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, regions);
                        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                        inputRegion.setAdapter(spinnerAdapter);


                        //Get ftp details.
                        ftpServer = response.getString("ftpServer");
                        ftpUserName = response.getString("userName");
                        ftpPassword = response.getString("password");
                        ftpDirectory = response.getString("cwd");
                    }

                } catch (JSONException ex) {
                    Log.e("newDriverActivity", "Failed to parse json. " + ex.getMessage());
                }
                showProgress(false);
            }

        });
    }

    private void showDriverPhotoImageSource() {
        //Show dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_image_source, null);
        builder.setView(dialogView);

        final Dialog dg = builder.create();
        dg.show();

        ((RadioGroup) dialogView.findViewById(R.id.inputChooseImageSource)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedRadioButtonID = group.getCheckedRadioButtonId();
                Log.d(logTag, "check change. Selected button id: " + selectedRadioButtonID);
                String imageSource = ((RadioButton) group.findViewById(selectedRadioButtonID)).getText().toString().toLowerCase();
                if (selectedRadioButtonID == R.id.inputUseCamera) {
                    //Use Camera
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, INTENT_REQUEST_CAMERA_DRIVER_PHOTO);
                    }
                } else {
                    //Use Gallery
                    Log.d(logTag, "check change. Open gallery");
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);//
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), INTENT_REQUEST_GALLERY_DRIVER_PHOTO);
                }
                dg.dismiss();
            }
        });


    }

    private void showDrivingLicenseImageSource() {
        //Show dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_image_source, null);
        builder.setView(dialogView);

        final Dialog dg = builder.create();
        dg.show();

        ((RadioGroup) dialogView.findViewById(R.id.inputChooseImageSource)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedRadioButtonID = group.getCheckedRadioButtonId();
                Log.d(logTag, "check change. Selected button id: " + selectedRadioButtonID);
                String imageSource = ((RadioButton) group.findViewById(selectedRadioButtonID)).getText().toString().toLowerCase();
                if (selectedRadioButtonID == R.id.inputUseCamera) {
                    //Use Camera
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, INTENT_REQUEST_CAMERA_DRIVING_LICENSE);
                    }
                } else {
                    //Use Gallery
                    Log.d(logTag, "check change. Open gallery");
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);//
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), INTENT_REQUEST_GALLERY_DRIVING_LICENSE);
                }

                dg.dismiss();
            }
        });

    }

    private void showVehicleInsurenceImageSource() {
        //Show dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_image_source, null);
        builder.setView(dialogView);

        final Dialog dg = builder.create();
        dg.show();

        ((RadioGroup) dialogView.findViewById(R.id.inputChooseImageSource)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedRadioButtonID = group.getCheckedRadioButtonId();
                Log.d(logTag, "check change. Selected button id: " + selectedRadioButtonID);
                String imageSource = ((RadioButton) group.findViewById(selectedRadioButtonID)).getText().toString().toLowerCase();
                if (selectedRadioButtonID == R.id.inputUseCamera) {
                    //Use Camera
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, INTENT_REQUEST_CAMERA_VEHICLE_INSURANCE);
                    }
                } else {
                    //Use Gallery
                    Log.d(logTag, "check change. Open gallery");
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);//
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), INTENT_REQUEST_GALLERY_VEHICLE_INSURANCE);
                }

                dg.dismiss();
            }
        });

    }

    private void showVehicleFrontImageSource() {
        //Show dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_image_source, null);
        builder.setView(dialogView);

        final Dialog dg = builder.create();
        dg.show();

        ((RadioGroup) dialogView.findViewById(R.id.inputChooseImageSource)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedRadioButtonID = group.getCheckedRadioButtonId();
                Log.d(logTag, "check change. Selected button id: " + selectedRadioButtonID);
                String imageSource = ((RadioButton) group.findViewById(selectedRadioButtonID)).getText().toString().toLowerCase();
                if (selectedRadioButtonID == R.id.inputUseCamera) {
                    //Use Camera
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, INTENT_REQUEST_CAMERA_VEHICLE_FRONT);
                    }
                } else {
                    //Use Gallery
                    Log.d(logTag, "check change. Open gallery");
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);//
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), INTENT_REQUEST_GALLERY_VEHICLE_FRONT);
                }
                dg.dismiss();
            }
        });
    }

    private void showVehicleSideImageSource() {
        //Show dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_image_source, null);
        builder.setView(dialogView);

        final Dialog dg = builder.create();
        dg.show();

        ((RadioGroup) dialogView.findViewById(R.id.inputChooseImageSource)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedRadioButtonID = group.getCheckedRadioButtonId();
                Log.d(logTag, "check change. Selected button id: " + selectedRadioButtonID);
                String imageSource = ((RadioButton) group.findViewById(selectedRadioButtonID)).getText().toString().toLowerCase();
                if (selectedRadioButtonID == R.id.inputUseCamera) {
                    //Use Camera
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, INTENT_REQUEST_CAMERA_VEHICLE_SIDE);
                    }
                } else {
                    //Use Gallery
                    Log.d(logTag, "check change. Open gallery");
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);//
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), INTENT_REQUEST_GALLERY_VEHICLE_SIDE);
                }
                dg.dismiss();
            }
        });
    }

    private void showVehicleBackImageSource() {
        //Show dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_image_source, null);
        builder.setView(dialogView);

        final Dialog dg = builder.create();
        dg.show();

        ((RadioGroup) dialogView.findViewById(R.id.inputChooseImageSource)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedRadioButtonID = group.getCheckedRadioButtonId();
                Log.d(logTag, "check change. Selected button id: " + selectedRadioButtonID);
                String imageSource = ((RadioButton) group.findViewById(selectedRadioButtonID)).getText().toString().toLowerCase();
                if (selectedRadioButtonID == R.id.inputUseCamera) {
                    //Use Camera
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, INTENT_REQUEST_CAMERA_VEHICLE_BACK);
                    }
                } else {
                    //Use Gallery
                    Log.d(logTag, "check change. Open gallery");
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);//
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), INTENT_REQUEST_GALLERY_VEHICLE_BACK);
                }
                dg.dismiss();
            }
        });
    }

    public void onDriverCreationSuccess() {

        Toast.makeText(getApplicationContext(), "Driver Created.", Toast.LENGTH_LONG).show();
        //Close activity and return to previous page.
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("loadFragment", "drivers");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public static Bitmap getResizedBitmap(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();
        float scaleWidth = ((float) 100) / width;
        float scaleHeight = ((float) 100) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }

    public void uploadDriverDocuments() {

        Log.d(logTag, "uplaoding driver documents.");

        //Upload Driver Photo
        DriverDocumentUploadService driverDocumentUploadService1 = new DriverDocumentUploadService();
        driverDocumentUploadService1.serverFileName = driverUniqueId;
        driverDocumentUploadService1.storageFilePath = File_Driver_Photo;
        driverDocumentUploadService1.execute(null, null, null);


        //Upload driving license
        DriverDocumentUploadService driverDocumentUploadService2 = new DriverDocumentUploadService();
        driverDocumentUploadService2.storageFilePath = File_Driving_License;
        driverDocumentUploadService2.serverFileName = driverUniqueId + "_dl";
        driverDocumentUploadService2.execute(null, null, null);

        //Upload vehicle insurence
        DriverDocumentUploadService driverDocumentUploadService3 = new DriverDocumentUploadService();
        driverDocumentUploadService3.storageFilePath = File_Vehicle_Insurance;
        driverDocumentUploadService3.serverFileName = driverUniqueId + "_vinsurance";
        driverDocumentUploadService3.execute(null, null, null);

        //Upload vehicle front
        DriverDocumentUploadService driverDocumentUploadService4 = new DriverDocumentUploadService();
        driverDocumentUploadService4.storageFilePath = File_Vehicle_Front;
        driverDocumentUploadService4.serverFileName = driverUniqueId + "_vfront";
        driverDocumentUploadService4.execute(null, null, null);

        //Upload vehicle side
        DriverDocumentUploadService driverDocumentUploadService5 = new DriverDocumentUploadService();
        driverDocumentUploadService5.storageFilePath = File_Vehicle_Side;
        driverDocumentUploadService5.serverFileName = driverUniqueId + "_vside";
        driverDocumentUploadService5.execute(null, null, null);

        //Upload vehicle back
        DriverDocumentUploadService driverDocumentUploadService6 = new DriverDocumentUploadService();
        driverDocumentUploadService6.storageFilePath = File_Vehicle_Back;
        driverDocumentUploadService6.serverFileName = driverUniqueId + "_vback";
        driverDocumentUploadService6.execute(null, null, null);

    }

    class DriverDocumentUploadService extends AsyncTask<Bitmap, Void, String> {

        public String serverFileName;
        public String storageFilePath;

        @Override
        protected String doInBackground(Bitmap... params) {
            try {

                FTPClient ftpClient = new FTPClient();
                ftpClient.connect(ftpServer);
                ftpClient.login(ftpUserName, ftpPassword);
                Log.d(logTag, ftpClient.getReplyString());
                Log.d(logTag, "current directory: " + ftpClient.printWorkingDirectory());
                ftpClient.changeWorkingDirectory(ftpDirectory);
                Log.d(logTag, ftpClient.getReplyString());
                Log.d(logTag, "Uploading file: " + storageFilePath + " to " + serverFileName);

                String ftpReply = ftpClient.getReplyString();
                if (ftpReply.contains("250")) {
                    ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);

                    //Preapare input stream.
                    serverFileName += ".jpg";
                    BufferedInputStream buffIn = null;
                    File file = new File(android.os.Environment.getExternalStorageDirectory(), storageFilePath);
                    buffIn = new BufferedInputStream(new FileInputStream(file));
                    ftpClient.enterLocalPassiveMode();


                    boolean result = ftpClient.storeFile(serverFileName, buffIn);
                    buffIn.close();
                    ftpClient.logout();
                    ftpClient.disconnect();

                    return "success";
                } else {
                    Log.d(logTag, "Invalid server response: " + ftpReply);
                    throw new IOException("Invalid ftp server response: " + ftpReply);
                }

            } catch (SocketException e) {
                Log.e(logTag, e.getStackTrace().toString());
                e.printStackTrace();
            } catch (UnknownHostException e) {
                Log.e(logTag, e.getStackTrace().toString());
                e.printStackTrace();
            } catch (IOException ex) {
                Log.e(logTag, "IO exception: " + ex.getMessage());
                ex.printStackTrace();
            } catch (Exception e) {
                Log.e(logTag, e.getStackTrace().toString());
                e.printStackTrace();
            }
            return "error";
        }

        @Override
        protected void onPostExecute(String msg) {
            super.onPostExecute(msg);
            if (msg.equals("success")) {
                imageIndex++;
                Log.d(logTag, "File Upload Complete: " + serverFileName + ", index: " + imageIndex);
                if (imageIndex == 6) {
                    onDriverCreationSuccess();
                }
            }
        }
    }
}


