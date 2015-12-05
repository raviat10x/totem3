package com.move10x.totem.design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.move10x.totem.R;
import com.move10x.totem.models.Booking;
import com.move10x.totem.models.CurrentProfile;
import com.move10x.totem.models.Driver;
import com.move10x.totem.models.JsonHttpResponseHandler;
import com.move10x.totem.models.Url;
import com.move10x.totem.services.AsyncHttpService;
import com.move10x.totem.services.CurrentProfileService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {

    View view;
    private static String logTag = "homeFragment";
    Context context;
    FloatingActionButton floatingActionButton;
    TextView txtUserName, txtUserType;
    TextView txtTotalDrivers, txTerminatedDrivers, txtDriverOnTrip, txtDriversAvailable, txtDriversOffduty, txtPendingDrivers;
    TableLayout tblDriverInfo;
    ProgressBar progressBar;

    TextView driverDetails;
    TextView txtDailyTrips;
    TextView txtWeeklyTrips;
    TextView txtAvailable;
    private DriverFragment.OnFragmentInteractionListener mListener;
    private TextView txtMonthlyTrips;
    private String TAG = "HOME FRAGMENT";
    private TextView txtMoveMiles;
    private LinearLayout layoutTotalDrivers;
    private LinearLayout layoutPending;
    private LinearLayout layoutAvailable;
    private LinearLayout layoutOnDuty;
    private LinearLayout layoutOffDuty;
    private LinearLayout layoutTerminated;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        txtUserName = (TextView) view.findViewById(R.id.txtUserName);
        txtUserType = (TextView) view.findViewById(R.id.txtUserType);
        tblDriverInfo = (TableLayout) view.findViewById(R.id.tblDriverInfo);
        progressBar = (ProgressBar) view.findViewById(R.id.driversProgress);
        txtTotalDrivers = (TextView) view.findViewById(R.id.txtTotalDrivers);
        txtDriverOnTrip = (TextView) view.findViewById(R.id.txtDriverOnTrip);
        txtDriversAvailable = (TextView) view.findViewById(R.id.txtDriversAvailable);
        txtDriversOffduty = (TextView) view.findViewById(R.id.txtDriversOffduty);
        txTerminatedDrivers = (TextView) view.findViewById(R.id.txTerminatedDrivers);
        txtPendingDrivers = (TextView) view.findViewById(R.id.txtPendingDrivers);

        driverDetails = (TextView) view.findViewById(R.id.driverDetails);
        txtDailyTrips = (TextView) view.findViewById(R.id.txtDailyTrips);
        txtWeeklyTrips = (TextView) view.findViewById(R.id.txtWeeklyTrips);
        txtMonthlyTrips = (TextView) view.findViewById(R.id.txtMonthlyTrips);
        txtMoveMiles = (TextView) view.findViewById(R.id.txtMoveMiles);
        layoutAvailable = (LinearLayout) view.findViewById(R.id.layoutAvailable);

        layoutTotalDrivers = (LinearLayout)view.findViewById(R.id.layoutTotalDrivers);
        layoutPending = (LinearLayout) view.findViewById(R.id.layoutPending);
        layoutOnDuty = (LinearLayout)view.findViewById(R.id.layoutOnDuty);
        layoutOffDuty = (LinearLayout)view.findViewById(R.id.layoutOffDuty);
        layoutTerminated = (LinearLayout)view.findViewById(R.id.layoutTerminated);

        getActivity().setTitle("Home");
        Log.d(logTag, "Fetching Current Profile Details");
        Context context = getActivity().getApplicationContext();
        CurrentProfile profile = new CurrentProfileService(context).getCurrentProfile();
        txtUserName.setText(profile.getUserName());
        txtUserType.setText(profile.getUserType().toUpperCase());
        Log.d(logTag, "Finished current fetching Profile Details." + profile.toString());


        txtTotalDrivers.setText("");
        txTerminatedDrivers.setText("");
        txtDriverOnTrip.setText("");
        txtDriversAvailable.setText("");
        txtDriversOffduty.setText("");
        txtPendingDrivers.setText("");

        //Fetch profile driver details.
        progressBar.setVisibility(View.VISIBLE);


        //Remove it........................
        ((CardView) view.findViewById(R.id.card_view)).setVisibility(View.VISIBLE);

        if (profile.getUserType().equals("VRM")) {
            fetchDriverInfo();

        } else if (profile.getUserType().equals("CRM")) {

            driverDetails.setText("Completed Booking");
            txtDailyTrips.setText("Today");
            txtDailyTrips.setTextColor(getResources().getColor(R.color.green));
            txtWeeklyTrips.setText("This Week");
            txtMonthlyTrips.setText("This Month");
            txtMonthlyTrips.setTextColor(getResources().getColor(R.color.colorAccent));
            layoutPending.setVisibility(View.GONE);
            layoutAvailable.setVisibility(View.GONE);
            txTerminatedDrivers.setTextColor(getResources().getColor(R.color.green));
            txtDriversOffduty.setTextColor(getResources().getColor(R.color.colorAccent));
            txtMoveMiles.setVisibility(View.VISIBLE);
            fetchCustomerInfo();
        }

        setOnClickListener();
        return view;
    }

    private void setOnClickListener() {
        layoutTotalDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fg = new DriverFragment();
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragementHolder, fg).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        layoutPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Inside layoutPending");
                DriverFragment fragment = new DriverFragment();
                fragment.currentStatus = "Pending";
                Log.d(TAG, "Outside layoutPending");
                Bundle bundle = new Bundle();
                bundle.putString("pending", "pending");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragementHolder, fragment).commit();

            }
        });
        layoutOffDuty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Inside layoutPending");
                DriverFragment fragment = new DriverFragment();
                fragment.currentStatus = "OffDuty";
                Log.d(TAG, "Outside layoutOffDuty");
                Bundle bundle = new Bundle();
                bundle.putString("offduty", "offduty");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragementHolder, fragment).commit();

            }
        });
        layoutOnDuty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Inside layoutOnDuty");
                DriverFragment fragment = new DriverFragment();
                fragment.currentStatus = "Pending";
                Log.d(TAG, "Outside layoutPending");
                Bundle bundle = new Bundle();
                bundle.putString("onduty", "onduty");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragementHolder, fragment).commit();

            }
        });
        layoutTerminated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Inside layoutTerminated");
                DriverFragment fragment = new DriverFragment();
                fragment.currentStatus = "Pending";
                Log.d(TAG, "Outside layoutTerminated");
                Bundle bundle = new Bundle();
                bundle.putString("terminated", "terminated");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragementHolder, fragment).commit();

            }
        });
        layoutAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Inside layoutAvailable");
                DriverFragment fragment = new DriverFragment();
                fragment.currentStatus = "Pending";
                Log.d(TAG, "Outside layoutAvailable");
                Bundle bundle = new Bundle();
                bundle.putString("available", "available");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragementHolder, fragment).commit();

            }
        });
    }

    TextClicked mCallback;

    public interface TextClicked{
        public void sendText(String text);
    }

    private void fetchCustomerInfo() {
        showProgress(true);

        final CurrentProfile currentProfile = new CurrentProfileService(getActivity().getApplicationContext()).getCurrentProfile();
        Log.d(logTag, "Fetch drivers for user: " + currentProfile.toString());
        String uid = currentProfile.getUserId();
        Log.d(logTag, "Fetch drivers for userId: " + uid);
        RequestParams tripParameters = new RequestParams();
        tripParameters.put("tag", "allBookingCount");
        tripParameters.put("crmId", uid);
        Log.d(TAG, "SDJFHDSKFBSD" + uid);

        //Async driver list fetch.
        AsyncHttpService.get(Url.totemApiUrl, tripParameters, new JsonHttpResponseHandler() {
                    @Override


                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // If the response is JSONObject instead of expected JSONArray.
                        Log.d(TAG, "Parsing getDriverList() response: " + response);
                        try {
                            if (response.getString("success") != null && response.getString("success").equals("1")) {
                                JSONObject jsonTripList = response.getJSONObject("msg");
                                Log.d(TAG, "Below Parsing List");
                                int todaysCnt = 0, weeklyCnt = 0, monthlyCnt = 0, totalTrips = 0;
                                int todayskm = 0, monthlykm = 0, weeklykm = 0;

                                int moveMiles = todayskm + monthlykm + weeklykm;

                                txtTotalDrivers.setText(String.valueOf(todaysCnt));
                                txTerminatedDrivers.setText(String.valueOf(todaysCnt));
                                txtDriverOnTrip.setText(String.valueOf(weeklyCnt));
                                txtDriversOffduty.setText(String.valueOf(monthlyCnt));
                                txtMoveMiles.setText("Move Miles : " + String.valueOf(moveMiles));

                            }
                        } catch (JSONException ex) {
                            Log.e(logTag, "json parsing error: " + ex.getMessage());
                            ex.printStackTrace();
                        }

                        showProgress(false);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable
                            throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable
                            throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String
                            responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                }

        );
    }

    public void onDriverAddClick() {
        Log.d(logTag, "on Add driver click.");
        Intent intent = new Intent(getActivity().getApplicationContext(), NewDriverActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    private void fetchDriverInfo() {

        showProgress(true);

        //Request Parameters.
        final CurrentProfile currentProfile = new CurrentProfileService(getActivity().getApplicationContext()).getCurrentProfile();
        Log.d(logTag, "Fetch drivers for user: " + currentProfile.toString());
        String uid = currentProfile.getUserId();
        Log.d(logTag, "Fetch drivers for userId: " + uid);
        RequestParams loginParameters = new RequestParams();
        loginParameters.put("uid", uid);
        loginParameters.put("role", currentProfile.getUserType());
        loginParameters.put("tag", "vrm_get_drivers_1");

        //Async driver list fetch.
        AsyncHttpService.get(Url.apiBaseUrl, loginParameters, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // If the response is JSONObject instead of expected JSONArray.
                        Log.d(logTag, "Parsing getDriverList() response: " + response);
                        try {
                            if (response.getString("success") != null && response.getString("success").equals("1")) {
                                JSONArray jsonDriverList = response.getJSONArray("drivers");
                                List<Driver> drivers = new ArrayList<Driver>();

                                int totalDrivers = 0, terminatedDrivers = 0, onTripDrivers = 0, availableDrivers = 0, offdutyDrivers = 0, pendingDrivers = 0;
                                for (int i = 0; i < jsonDriverList.length(); i++) {

                                    //Read driver
                                    Driver currentDriver = Driver.decodeJsonForList(jsonDriverList.getJSONObject(i));
                                    drivers.add(currentDriver);

                                    //Calculate category wise count.
                                    totalDrivers++;
                                    if (currentDriver.getWorkStatus().equals(Driver.WorkStatus_Terminated)) {

                                        //If driver not active, set color red for work and hide duty status.
                                        terminatedDrivers++;

                                    } else if (currentDriver.getWorkStatus().contains("PENDING")) {
                                        pendingDrivers++;
                                    } else if (currentDriver.getWorkStatus().equals(Driver.WorkStatus_Active)) {

                                        //If work status is active, check for duty status.
                                        if (currentDriver.getDutyStatus().equals(Driver.DutyStatus_Available))
                                            availableDrivers++;
                                        else if (currentDriver.getDutyStatus().equals(Driver.DutyStatus_Offduty))
                                            offdutyDrivers++;
                                        else
                                            onTripDrivers++;
                                    }
                                }

                                Log.d(logTag, "Json Parsed.");
                                txtTotalDrivers.setText(Integer.toString(totalDrivers));
                                txTerminatedDrivers.setText(Integer.toString(terminatedDrivers));
                                txtDriverOnTrip.setText(Integer.toString(onTripDrivers));
                                txtDriversAvailable.setText(Integer.toString(availableDrivers));
                                txtDriversOffduty.setText(Integer.toString(offdutyDrivers));
                                txtPendingDrivers.setText(Integer.toString(pendingDrivers));

                            }
                        } catch (JSONException ex) {
                            Log.e(logTag, "json parsing error: " + ex.getMessage());
                            ex.printStackTrace();
                        }

                        showProgress(false);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable
                            throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable
                            throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String
                            responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                }

        );
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        Log.d(logTag, "Toggle progress bar. Set visiblity to: " + show);
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            tblDriverInfo.setVisibility(show ? View.GONE : View.VISIBLE);
            tblDriverInfo.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tblDriverInfo.setVisibility(show ? View.GONE : View.VISIBLE);
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
            tblDriverInfo.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


//    @Override
//    public void onAttach(Fragment fragment) {
//        super.onAttach(fragment);
//
//        // This makes sure that the container activity has implemented
//        // the callback interface. If not, it throws an exception
//        try {
//            mCallback = (TextClicked) fragment;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(fragment.toString()
//                    + " must implement TextClicked");
//        }
//    }
public void someMethod(){
    mCallback.sendText("Pending");
}

    @Override
    public void onDetach() {
        mCallback = null; // => avoid leaking, thanks @Deepscorn
        super.onDetach();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (TextClicked) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TextClicked");
        }
    }
}
