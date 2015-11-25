package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.CurrentProfile;
import com.move10x.totem.models.Customer;
import com.move10x.totem.models.GPSTracker;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;
import com.move10x.totem.services.CurrentProfileService;
import com.move10x.totem.services.IOService;
import com.move10x.totem.services.MiscService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class NewCustomerActivity extends AppCompatActivity {

    private static final String TAG = "NewCustomerActivity";
    static final String File_Customer_VCard = "customerPic.jpeg";
//    static final String File_Customer_VCard = "";
    private static final int INTENT_REQUEST_CAMERA_CUSTOMER_VCARD = 1002;
    private static final int INTENT_REQUEST_GALLERY_CUSTOMER_PHOTO = 1003;
    private EditText txtMobileNumber;
    private EditText txtAddress;
    private EditText txtLandline;
    private TextView txtDOB;
    private Button btnDOB;
    private TextView txtAnnivarsary;
    private Button btnAnnivarsary;
    private Spinner txtfvrtVehicle;
    private ScrollView newCustomerViewContainer;
    private ProgressBar progressBar;
    private Date dobDate = null;
    private Date wedDate = null;
    private ImageButton btnUploadCustomerVCard;
    private TextView txtVCard;
    private DatePickerDialog.OnDateSetListener onDateSet;
    private AppCompatButton btnAddCustomer;
    private Spinner txtBillingInfo;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtArea;
    private Spinner txtCity;
    private EditText txtPin;
    private TextView txtCustomerCurrentLocation;
    private AppCompatButton getLocation;
    private EditText txtEmail;
    private String customerUniqueId;
    private String ftpServer, ftpUserName, ftpPassword, ftpDirectory;
    private static String customerPicFilePath = null;
    private EditText companyName;
    boolean uploadbuttonclicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Lead");


        GPSTracker gpsTracker = new GPSTracker(this);

        if (gpsTracker.getIsGPSTrackingEnabled()) {
            String loc = String.valueOf(gpsTracker.getLatitude() + "   " + gpsTracker.getLongitude());

            txtCustomerCurrentLocation = (TextView) findViewById(R.id.txtCustomerCurrentLocation);
            txtCustomerCurrentLocation.setText(loc);
            Log.d(TAG, loc);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }

//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)‌​;
        txtMobileNumber = (EditText) findViewById(R.id.txtMobileNumber);
        txtVCard = (TextView) findViewById(R.id.txtVCard);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtLandline = (EditText) findViewById(R.id.txtLandline);
        txtDOB = (TextView) findViewById(R.id.txtDOB);
        btnDOB = (Button) findViewById(R.id.btnDOB);
        txtAnnivarsary = (TextView) findViewById(R.id.txtAnnivarsary);
        btnAnnivarsary = (Button) findViewById(R.id.btnAnnivarsary);
        txtfvrtVehicle = (Spinner) findViewById(R.id.txtfvrtVehicle);
        btnUploadCustomerVCard = (ImageButton) findViewById(R.id.btnUploadCustomerVCard);
        txtVCard = (TextView) findViewById(R.id.txtVCard);
        newCustomerViewContainer = (ScrollView) findViewById(R.id.newCustomerViewContainer);
        progressBar = (ProgressBar) findViewById(R.id.addCustomerProgress);
        btnAddCustomer = (AppCompatButton) findViewById(R.id.btnSignup);
        txtBillingInfo = (Spinner) findViewById(R.id.txtBillingInfo);
        txtfvrtVehicle = (Spinner) findViewById(R.id.txtfvrtVehicle);
        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtMobileNumber = (EditText) findViewById(R.id.txtMobileNumber);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtArea = (EditText) findViewById(R.id.txtArea);
        txtCity = (Spinner) findViewById(R.id.txtCity);
        txtPin = (EditText) findViewById(R.id.txtPin);
        txtLandline = (EditText) findViewById(R.id.txtLandline);
        companyName = (EditText) findViewById(R.id.companyName);

        uploadbuttonclicked = false;
//        txtCustomerCurrentLocation = (TextView) findViewById(R.id.txtCustomerCurrentLocation);
        RegisterButtonClickEvents();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d(TAG, "At activity result. requestCode = " + requestCode + " resultCode = " + resultCode);

        // Customer Photo Response
        if (requestCode == INTENT_REQUEST_CAMERA_CUSTOMER_VCARD && resultCode == RESULT_OK) {
//            Log.d(TAG, "response came from Customer's VCARD from Camera.");

            //Retrieve Bitmap image.
            Bundle extras = data.getExtras();
            Bitmap bitmapCustomerPhoto = getResizedBitmap((Bitmap) extras.get("data"));
//            Log.i(TAG,"Cutstomer Photo -- "+bitmapCustomerPhoto);
            (new IOService()).writeLargeImageToFile(File_Customer_VCard, (Bitmap) extras.get("data"));

            //Set image to bitmap view.
            btnUploadCustomerVCard.setImageBitmap(bitmapCustomerPhoto);
            btnUploadCustomerVCard.invalidate();
            bitmapCustomerPhoto = null;

        } else if (requestCode == INTENT_REQUEST_GALLERY_CUSTOMER_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Log.d(TAG, "Response from driver photo from gallery.");

            android.net.Uri selectedImagePath = data.getData();
//            Log.d(TAG, "URI: " + selectedImagePath.getPath());
            try {
                //Retrieve Bitmap image.
                Bitmap originalDriverPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath);
                Bitmap bitmapCustomerPhoto = getResizedBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath));
                (new IOService()).writeLargeImageToFile(File_Customer_VCard, originalDriverPhoto);

                //Set bitmap to imageview.
                btnUploadCustomerVCard.setImageBitmap(bitmapCustomerPhoto);
                btnUploadCustomerVCard.invalidate();
                bitmapCustomerPhoto = null;

                //Hide camera options.
//                pnlDriverPhoto.setVisibility(View.VISIBLE);
//                btnUploadDriverPhoto.setVisibility(View.GONE);

            } catch (FileNotFoundException e) {
//                Log.e(TAG, e.getMessage());
            } catch (IOException e) {
//                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void RegisterButtonClickEvents() {

        btnUploadCustomerVCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadbuttonclicked = true;
                showCustomerPhotoImageSource();

            }
        });
        btnDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "Inside dob date");
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
                DatePickerDialog.OnDateSetListener onDateSet = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        dobDate = calendar.getTime();
                        String dateText = new SimpleDateFormat("dd/MM/yy").format(calendar.getTime());
                        btnDOB.setText(dateText);
                    }
                };

                date.setCallBack(onDateSet);
                date.show(getSupportFragmentManager(), "Date Picker");


            }
        });
        btnAnnivarsary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                DatePickerDialog.OnDateSetListener onDateSet = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        wedDate = calendar.getTime();
                        String dateText = new SimpleDateFormat("dd/MM/yy").format(calendar.getTime());
//                        Log.d(TAG, "Dates are : " + dateText.toString());
                        btnAnnivarsary.setText(dateText);
//                        Log.d(TAG, "Dates are : " + txtDOB + " " + txtAnnivarsary);
                    }
                };

                date.setCallBack(onDateSet);
                date.show(getSupportFragmentManager(), "Date Picker");

            }
        });

        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCreateCustomerClick();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void OnCreateCustomerClick() {
//        Log.d(TAG, "OnCustomerClick");

        int imageIndex = 0;

        Boolean result = validateCustomerFields();
        if (result) {

            showProgress(true);
            //Create new user.
            Customer newCustomer = new Customer();
//            newCustomer.setUid(customerUniqueId.g);
            newCustomer.setFirstName(txtFirstName.getText().toString().trim());
            newCustomer.setLastName(txtLastName.getText().toString().trim());
            newCustomer.setMobile(txtMobileNumber.getText().toString().trim());
            newCustomer.setEmail(txtEmail.getText().toString().trim());
            newCustomer.setAddress(txtAddress.getText().toString().trim());
            newCustomer.setArea(txtArea.getText().toString().trim());
            newCustomer.setCity(txtCity.getSelectedItem().toString());
            newCustomer.setPin(txtPin.getText().toString().trim());
            newCustomer.setlandline(txtLandline.getText().toString().trim());
            if (dobDate == null) {
                newCustomer.setDob("");
            } else {
                newCustomer.setDob(new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(dobDate.getTime()));
            }
            if (wedDate == null) {
                newCustomer.setAnnivarsary("");
            } else {
                newCustomer.setAnnivarsary(new SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(wedDate.getTime()));
            }

            newCustomer.setBillName(txtBillingInfo.getSelectedItem().toString());
            newCustomer.setFvrtVehicle(txtfvrtVehicle.getSelectedItem().toString());
            newCustomer.setCompanyName(companyName.getText().toString().trim());
            newCustomer.setCustomerLocation(txtCustomerCurrentLocation.getText().toString().trim());

//            showProgress(false);
            CurrentProfile cp = (new CurrentProfileService(getApplicationContext())).getCurrentProfile();

            //Request Parameters.
            RequestParams requestParams = new RequestParams();
            requestParams.put("crmId", customerUniqueId);
            requestParams.put("address", txtAddress.getText().toString());
            requestParams.put("fName", txtFirstName.getText().toString().trim());
            requestParams.put("lName", txtLastName.getText().toString().trim());
            requestParams.put("mobile", txtMobileNumber.getText().toString().trim());
//            String email = txtEmail.getText().toString().trim() == "" ? null : txtEmail.getText().toString().trim();

            Log.i("NULL", "Landline" + txtLandline.getText().toString() + " \n " + txtEmail.getText().toString());
            requestParams.put("email", txtEmail.getText().toString().trim());
            Log.i("NULL", "Landline" + txtLandline.getText().toString() + " \n " + txtEmail.getText().toString());
//            requestParams.put("address", txtAddress.getText().toString().trim());
            requestParams.put("city", txtCity.getSelectedItem().toString());
            requestParams.put("area", txtArea.getText().toString().trim());
            requestParams.put("pin", txtPin.getText().toString().trim());
            requestParams.put("currentLocation", txtCustomerCurrentLocation.getText().toString());
            requestParams.put("landline", txtLandline.getText().toString().trim());
            requestParams.put("business", companyName.getText().toString().trim());

//            try {
//                File myFile = new File();
//                requestParams.put("vCardImg", myFile);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }

            CurrentProfile profile = new CurrentProfileService(this).getCurrentProfile();
            requestParams.put("crmId", profile.getUserId());
            requestParams.put("crmFName", profile.getFirstName());
            requestParams.put("crmLName", profile.getLastName());


            File myFile = new File(android.os.Environment.getExternalStorageDirectory(), File_Customer_VCard);

            try {
//                Log.d(TAG, "Inside myFiles");
                if(uploadbuttonclicked) {
                    requestParams.put("vCardImg", myFile);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            requestParams.put("favVehicle", txtfvrtVehicle.getSelectedItem().toString());
            requestParams.put("billName", txtBillingInfo.getSelectedItem().toString());


            requestParams.put("appversion", "");
            if (dobDate == null) {
                requestParams.put("dob", "");
            } else {
                requestParams.put("dob", (new SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(dobDate.getTime())));
            }
            if (wedDate == null) {
                requestParams.put("dob", "");
            } else {
                requestParams.put("anniversary", (new SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(wedDate.getTime())));
            }
            requestParams.put("tag", "createUser");
            Log.d("Adding Customer ", requestParams.toString());

//            Log.d(TAG, "creating c: " + newCustomer.toString());


            //Async customerlist fetch.
            AsyncHttpService.post(Url.addCustomerUrl, requestParams, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d(TAG, "At create customer success response: " + response);
                    Log.d(TAG, "At create customer success response:header " + headers);
//                    Log.d(TAG, "At create customer success response: " + response);
                    try {
                        int errorCode = response.getInt("error");
//                        Log.d(TAG, "Inside try block: " + response);
                        if (errorCode == 0) {
                            //Success
//                            Log.d(TAG, "Customer created successfully. Uploading documents now." + response);
                            onCustomerCreationSuccess();
                            showProgress(false);
                        } else {
                            //Error

                            Toast.makeText(getApplicationContext(), response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException ex) {
                        Toast.makeText(getBaseContext(), "Internal error occured.", Toast.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Toast.makeText(getBaseContext(), "Failed to reach server. Status: " + statusCode + ", json: " + errorResponse, Toast.LENGTH_LONG);
//                    Log.d(TAG, "Failed to create customer. Status: " + statusCode + ", Server Response: " + errorResponse);
                }

            });
            showProgress(false);

        }
//        else
//            Log.d(TAG, "In else part");
//        finish();
    }


    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            newCustomerViewContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            newCustomerViewContainer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    newCustomerViewContainer.setVisibility(show ? View.GONE : View.VISIBLE);
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
            newCustomerViewContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private Boolean validateCustomerFields() {

        //Clear previous errors.
//        txtUploadDocumentsError.setVisibility(View.GONE);

        //Validate First Name


        if (MiscService.isValidName(txtFirstName.getText().toString().trim())) {
            if (MiscService.isValidName(txtLastName.getText().toString().trim())) {
                if (MiscService.isValidMobile(txtMobileNumber.getText().toString().trim())) {
//                    if (MiscService.isValidEmail(txtEmail.getText().toString().trim())) {
                        if (MiscService.isValidName(txtAddress.getText().toString().trim())) {
                            if (MiscService.isValidName(txtArea.getText().toString().trim())) {
                                if (MiscService.isValidPin(txtPin.getText().toString().trim())) {
                                    if (MiscService.isValidName(btnDOB.getText().toString().trim()) && !(btnDOB.getText().toString().trim().equalsIgnoreCase("select")))
                                    {
                                        Log.d(TAG, "Hey its clicked" + btnDOB.getText().toString());
                                        return true;
                                    }
                                    else{
                                        btnDOB.setError("Please provide DOB");
                                        return false;
                                    }
                                } else {
                                    txtPin.setError("Not valid pin");
                                    return false;
                                }
                            } else {
                                txtArea.setError("Not valid region");
                                return false;
                            }
                        } else {
                            txtAddress.setError("Not valid address");
                            return false;
                        }
//                    } else {
//                        txtEmail.setError("Not valid email id");
//                        return false;
//                    }
                } else {
                    txtMobileNumber.setError("Not valid mobile number");
                    return false;
                }
            } else {
                txtLastName.setError("Provide Last Name");
                return false;
            }
        } else {
            txtFirstName.setError("Provide First Name");
            return false;
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


    private void onCustomerCreationSuccess() {

        Toast.makeText(getApplicationContext(), "Customer Created.", Toast.LENGTH_LONG).show();
        Log.i("Customer Added Succesfy", "Thank you");
        //Close activity and return to previous page.
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.putExtra("loadFragment", "customers");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
        finish();
    }

    private void showCustomerPhotoImageSource() {
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
//                Log.d(TAG, "check change. Selected button id: " + selectedRadioButtonID);
                String imageSource = ((RadioButton) group.findViewById(selectedRadioButtonID)).getText().toString().toLowerCase();
                if (selectedRadioButtonID == R.id.inputUseCamera) {
                    //Use Camera
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, INTENT_REQUEST_CAMERA_CUSTOMER_VCARD);
                    }
                } else {
                    //Use Gallery
//                    Log.d(TAG, "check change. Open gallery");
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);//
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), INTENT_REQUEST_GALLERY_CUSTOMER_PHOTO);
                }
                dg.dismiss();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.d("customerDetails", "onmenuitem click. " + item.getItemId() + "," + R.id.home);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
                
